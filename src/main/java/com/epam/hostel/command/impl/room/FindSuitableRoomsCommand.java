package com.epam.hostel.command.impl.room;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.DiscountService;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 19.12.2016.
 */
public class FindSuitableRoomsCommand implements Command {

    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String MAIN_PAGE = "/Controller?command=mainPage";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/order.jsp";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String SEARCH_FORM_SEATS_NUMBER_PARAM = "searchFormSeatsNumber";
    private static final String SEARCH_FORM_CHECK_IN_DATE_PARAM = "searchFormCheckInDate";
    private static final String SEARCH_FORM_DAYS_STAY_NUMBER_PARAM = "searchFormDaysStayNumber";

    private static final String USER_DISCOUNTS_REQUEST_ATTR = "discounts";
    private static final String ROOMS_REQUEST_ATTR = "rooms";
    private static final String SEATS_NUMBER_REQUEST_ATTR = "seatsNumber";
    private static final String CHECK_IN_DATE_REQUEST_ATTR = "checkInDate";
    private static final String DAYS_STAY_NUMBER_REQUEST_ATTR = "daysStayNumber";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? true : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if(userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String searchFormSeatsNumberString = request.getParameter(SEARCH_FORM_SEATS_NUMBER_PARAM);
        int searchFormSeatsNumber = -1;
        if(searchFormSeatsNumberString != null){
            try{
                searchFormSeatsNumber = Integer.parseInt(searchFormSeatsNumberString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong seats number for find suitable rooms");
            }
        }

        String searchFormCheckInDateString = request.getParameter(SEARCH_FORM_CHECK_IN_DATE_PARAM);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date searchFormCheckInDate = null;
        if (searchFormCheckInDateString != null) {
            try {
                searchFormCheckInDate = format.parse(searchFormCheckInDateString);
            } catch (ParseException | NullPointerException e) {
                LOGGER.error("Wrong check in date for find suitable rooms");
            }
        }

        String searchFormDaysStayNumberString = request.getParameter(SEARCH_FORM_DAYS_STAY_NUMBER_PARAM);
        int searchFormDaysStayNumber = -1;
        if(searchFormDaysStayNumberString != null){
            try{
                searchFormDaysStayNumber = Integer.parseInt(searchFormDaysStayNumberString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong days stay number for find suitable rooms");
            }
        }

        if (searchFormCheckInDate != null) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                RoomService roomService = serviceFactory.getRoomService();
                List<Room> rooms = roomService.getAllSuitableRooms(searchFormCheckInDate, searchFormDaysStayNumber, searchFormSeatsNumber);
                request.setAttribute(ROOMS_REQUEST_ATTR, rooms);

                int userId = (Integer)session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
                DiscountService discountService = serviceFactory.getDiscountService();
                List<Discount> discounts = discountService.getAllUnusedDiscountsByClientId(userId);
                request.setAttribute(USER_DISCOUNTS_REQUEST_ATTR, discounts);

                request.setAttribute(CHECK_IN_DATE_REQUEST_ATTR, searchFormCheckInDateString);
                request.setAttribute(SEATS_NUMBER_REQUEST_ATTR, searchFormSeatsNumber);
                request.setAttribute(DAYS_STAY_NUMBER_REQUEST_ATTR, searchFormDaysStayNumber);
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } else {
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }
    }
}
