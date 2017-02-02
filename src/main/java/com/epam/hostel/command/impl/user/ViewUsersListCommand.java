package com.epam.hostel.command.impl.user;

import com.epam.hostel.bean.entity.PagedList;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Services request to the users list page.
 */
public class ViewUsersListCommand implements Command {
    private final static Logger logger = Logger.getLogger(ViewUsersListCommand.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/users.jsp";
    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=mainPage";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String USERS_REQUEST_ATTR = "users";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    private static final String PAGE_PARAM = "page";

    private static final int AMOUNT = 10;
    private static final int DEFAULT_PAGE = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? false : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if (!userRole) {
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        int page = CommandHelper.getInt(request.getParameter(PAGE_PARAM));
        if (page == -1){
            page = DEFAULT_PAGE;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            List<User> users = userService.getAllUsers((page-1)*AMOUNT, AMOUNT);
            int count = userService.getUsersCount();

            PagedList<User> pagedList = new PagedList<>();
            pagedList.setContent(users);
            pagedList.setLastPage((int)Math.ceil(count/(double)AMOUNT));
            pagedList.setCurrentPage(page);

            request.setAttribute(USERS_REQUEST_ATTR, pagedList);
        } catch (ServiceException e) {
            logger.warn(e);
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
