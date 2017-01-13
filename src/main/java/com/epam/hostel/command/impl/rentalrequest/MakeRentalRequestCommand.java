package com.epam.hostel.command.impl.rentalrequest;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
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
 * Created by ASUS on 19.12.2016.
 */
public class MakeRentalRequestCommand implements Command {

    private final static Logger LOGGER = Logger.getRootLogger();

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
        if(userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        String rentalRequestFormSeatsNumberString = request.getParameter(RENTAL_REQUEST_FORM_SEATS_NUMBER_PARAM);
        int rentalRequestFormSeatsNumber = -1;
        if(rentalRequestFormSeatsNumberString != null){
            try{
                rentalRequestFormSeatsNumber = Integer.parseInt(rentalRequestFormSeatsNumberString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong seats number for make rental request");
            }
        }

        String rentalRequestFormCheckInDateString = request.getParameter(RENTAL_REQUEST_FORM_CHECK_IN_DATE_PARAM);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date rentalRequestFormCheckInDate = null;
        if (rentalRequestFormCheckInDateString != null) {
            try {
                rentalRequestFormCheckInDate = format.parse(rentalRequestFormCheckInDateString);
            } catch (ParseException | NullPointerException e) {
                LOGGER.error("Wrong check in date for make rental request");
            }
        }

        String rentalRequestFormDaysStayNumberString = request.getParameter(RENTAL_REQUEST_FORM_DAYS_STAY_NUMBER_PARAM);
        int rentalRequestFormDaysStayNumber = -1;
        if(rentalRequestFormDaysStayNumberString != null){
            try{
                rentalRequestFormDaysStayNumber = Integer.parseInt(rentalRequestFormDaysStayNumberString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong days stay number for make rental request");
            }
        }

        String rentalRequestFormRoomIdString = request.getParameter(RENTAL_REQUEST_FORM_ROOM_PARAM);
        int rentalRequestFormRoomId = -1;
        if(rentalRequestFormRoomIdString != null){
            try{
                rentalRequestFormRoomId = Integer.parseInt(rentalRequestFormRoomIdString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong room id for make rental request");
            }
        }

        String rentalRequestFormTypeString = request.getParameter(RENTAL_REQUEST_FORM_TYPE_PARAM);
        int rentalRequestFormType = -1;
        if(rentalRequestFormTypeString != null){
            try{
                rentalRequestFormType = Integer.parseInt(rentalRequestFormTypeString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong type number for make rental request");
            }
        }

        String rentalRequestFormDiscountString = request.getParameter(RENTAL_REQUEST_FORM_DISCOUNT_PARAM);
        int rentalRequestFormDiscount = -1;
        if(rentalRequestFormDiscountString != null){
            try{
                rentalRequestFormDiscount = Integer.parseInt(rentalRequestFormDiscountString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong discount for make rental request");
            }
        }

        String rentalRequestFormPaymentString = request.getParameter(RENTAL_REQUEST_FORM_PAYMENT_PARAM);
        int rentalRequestFormPayment = -1;
        if(rentalRequestFormDaysStayNumberString != null){
            try{
                rentalRequestFormPayment = Integer.parseInt(rentalRequestFormPaymentString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong payment for make rental request");
            }
        }

        if (rentalRequestFormCheckInDate != null) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                RentalRequestService rentalRequestService = serviceFactory.getRentalRequestService();

                int userId = (Integer)session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
                User client = new User();
                client.setId(userId);
                RentalRequest rentalRequest = new RentalRequest();
                rentalRequest.setClient(client);
                rentalRequest.setSeatsNumber(rentalRequestFormSeatsNumber);
                rentalRequest.setCheckInDate(rentalRequestFormCheckInDate);
                rentalRequest.setDaysStayNumber(rentalRequestFormDaysStayNumber);

                ScheduleRecord scheduleRecord = new ScheduleRecord();
                scheduleRecord.setRoomNumber(rentalRequestFormRoomId);
                scheduleRecord.setCheckInDate(rentalRequestFormCheckInDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rentalRequestFormCheckInDate);
                calendar.add(Calendar.DATE, rentalRequestFormDaysStayNumber);
                Date checkOutDate = calendar.getTime();
                scheduleRecord.setCheckoutDate(checkOutDate);

                if (rentalRequestFormType == 1){
                    rentalRequest.setFullPayment(true);
                    rentalRequest.setPayment(rentalRequestFormPayment);
                } else {
                    rentalRequest.setFullPayment(false);
                    scheduleRecord.setPaymentDuty(rentalRequestFormPayment);
                }

                rentalRequestService.makeRentalRequest(scheduleRecord, rentalRequest, rentalRequestFormDiscount);

                response.sendRedirect(REDIRECT_PAGE+MAKE_ORDER_SUCCESS_REQUEST_ATTR+EQ+true);
            } catch (ServiceException e) {
                response.sendRedirect(REDIRECT_PAGE+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
            }
        }
    }
}
