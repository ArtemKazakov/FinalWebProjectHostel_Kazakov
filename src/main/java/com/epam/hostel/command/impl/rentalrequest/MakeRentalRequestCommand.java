package com.epam.hostel.command.impl.rentalrequest;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.service.RentalRequestService;
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
import java.util.Calendar;
import java.util.Date;

/**
 * Services request from the making rental request form.
 */
public class MakeRentalRequestCommand implements Command {

    private final static Logger logger = Logger.getLogger(MakeRentalRequestCommand.class);

    private static final String MAIN_PAGE = "/Controller?command=mainPage";
    private static final String REDIRECT_PAGE = "Controller?command=findSuitableRooms&";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String RENTAL_REQUEST_FORM_SEATS_NUMBER_PARAM = "rentalRequestFormSeatsNumber";
    private static final String RENTAL_REQUEST_FORM_CHECK_IN_DATE_PARAM = "rentalRequestFormCheckInDate";
    private static final String RENTAL_REQUEST_FORM_DAYS_STAY_NUMBER_PARAM = "rentalRequestFormDaysStayNumber";
    private static final String RENTAL_REQUEST_FORM_ROOM_PARAM = "rentalRequestFormRoom";
    private static final String RENTAL_REQUEST_FORM_TYPE_PARAM = "rentalRequestFormType";
    private static final String RENTAL_REQUEST_FORM_DISCOUNT_PARAM = "rentalRequestFormDiscount";
    private static final String RENTAL_REQUEST_FORM_PAYMENT_PARAM = "rentalRequestFormPayment";

    private static final String MAKE_ORDER_SUCCESS_REQUEST_ATTR = "makeOrderSuccess";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? true : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if (userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        int seatsNumber = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_FORM_SEATS_NUMBER_PARAM));

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date checkInDate = null;
        if (request.getParameter(RENTAL_REQUEST_FORM_CHECK_IN_DATE_PARAM) != null) {
            try {
                checkInDate = format.parse(request.getParameter(RENTAL_REQUEST_FORM_CHECK_IN_DATE_PARAM));
            } catch (ParseException | NullPointerException e) {
                logger.error("Wrong check in date for make rental request");
                response.sendRedirect(MAIN_PAGE);
                return;
            }
        }

        int daysStayNumber = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_FORM_DAYS_STAY_NUMBER_PARAM));
        int roomId = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_FORM_ROOM_PARAM));
        int type = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_FORM_TYPE_PARAM));
        int discount = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_FORM_DISCOUNT_PARAM));
        int payment = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_FORM_PAYMENT_PARAM));


        try {
            RentalRequestService rentalRequestService = ServiceFactory.getInstance().getRentalRequestService();

            int userId = (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
            User client = new User();
            client.setId(userId);
            RentalRequest rentalRequest = new RentalRequest();
            rentalRequest.setClient(client);
            rentalRequest.setSeatsNumber(seatsNumber);
            rentalRequest.setCheckInDate(checkInDate);
            rentalRequest.setDaysStayNumber(daysStayNumber);

            ScheduleRecord scheduleRecord = new ScheduleRecord();
            scheduleRecord.setRoomNumber(roomId);
            scheduleRecord.setCheckInDate(checkInDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DATE, daysStayNumber);
            Date checkOutDate = calendar.getTime();
            scheduleRecord.setCheckoutDate(checkOutDate);

            if (type == 1) {
                rentalRequest.setFullPayment(true);
                rentalRequest.setPayment(payment);
            } else {
                rentalRequest.setFullPayment(false);
                scheduleRecord.setPaymentDuty(payment);
            }

            rentalRequestService.makeRentalRequest(scheduleRecord, rentalRequest, discount);

            response.sendRedirect(REDIRECT_PAGE + MAKE_ORDER_SUCCESS_REQUEST_ATTR + EQ + true);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(REDIRECT_PAGE + SERVICE_ERROR_REQUEST_ATTR + EQ + true);
        }

    }
}
