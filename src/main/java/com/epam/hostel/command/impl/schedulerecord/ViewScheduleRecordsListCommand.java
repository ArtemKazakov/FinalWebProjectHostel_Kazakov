package com.epam.hostel.command.impl.schedulerecord;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.ScheduleRecordService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 09.01.2017.
 */
public class ViewScheduleRecordsListCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/schedule.jsp";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String SCHEDULE_RECORDS_REQUEST_ATTR = "scheduleRecords";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? false : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if(!userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ScheduleRecordService scheduleRecordService = serviceFactory.getScheduleRecordService();
            List<ScheduleRecord> scheduleRecords = scheduleRecordService.getAllScheduleRecords();
            request.setAttribute(SCHEDULE_RECORDS_REQUEST_ATTR, scheduleRecords);
        } catch (ServiceException e){
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
