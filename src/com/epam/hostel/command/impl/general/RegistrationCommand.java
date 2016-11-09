package com.epam.hostel.command.impl.general;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.service.SiteService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS on 29.10.2016.
 */
public class RegistrationCommand implements Command {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/registration.jsp";

    private static final String REGISTRATION_FORM_LOGIN_PARAM = "registrationFormLogin";
    private static final String REGISTRATION_FORM_PASSWORD_PARAM = "registrationFormPassword";
    private static final String REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM = "registrationIdentificationNumber";
    private static final String REGISTRATION_FORM_SERIES_PARAM = "registrationFormSeries";
    private static final String REGISTRATION_FORM_SURNAME_PARAM = "registrationFormSurname";
    private static final String REGISTRATION_FORM_NAME_PARAM = "registrationFormName";
    private static final String REGISTRATION_FORM_LAST_NAME_PARAM = "registrationFormLastName";
    private static final String REGISTRATION_FORM_BIRTHDAY_DATE_PARAM = "registrationBirthdayDate";

    private static final String REGISTRATION_SUCCESS_REQUEST_ATTR = "registrationSuccess";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";

    private static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String registrationFormLogin = request.getParameter(REGISTRATION_FORM_LOGIN_PARAM);
        String registrationFormPassword = request.getParameter(REGISTRATION_FORM_PASSWORD_PARAM);
        String registrationIdentificationNumberString = request.getParameter(REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM);
        int registrationIdentificationNumber = -1;
        if(registrationIdentificationNumberString != null){
            try{
                registrationIdentificationNumber = Integer.parseInt(registrationIdentificationNumberString);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        String registrationFormSeries = request.getParameter(REGISTRATION_FORM_SERIES_PARAM);
        String registrationFormSurname = request.getParameter(REGISTRATION_FORM_SURNAME_PARAM);
        String registrationFormName = request.getParameter(REGISTRATION_FORM_NAME_PARAM);
        String registrationFormLastName = request.getParameter(REGISTRATION_FORM_LAST_NAME_PARAM);
        String registrationBirthdayDateString = request.getParameter(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM);
        SimpleDateFormat format = new SimpleDateFormat(BIRTHDAY_DATE_FORMAT);
        Date registrationBirthdayDate = null;
        if (registrationBirthdayDateString != null) {
            try {
                registrationBirthdayDate = format.parse(request.getParameter(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM));
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (registrationFormLogin != null && registrationFormPassword != null &&
                registrationFormSeries != null && registrationFormSurname != null && registrationFormName != null &&
                registrationFormLastName != null) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                siteService.registration(registrationFormLogin, registrationFormPassword, registrationIdentificationNumber,
                        registrationFormSeries, registrationFormSurname, registrationFormName, registrationFormLastName,
                        registrationBirthdayDate);
                request.setAttribute(REGISTRATION_SUCCESS_REQUEST_ATTR, true);
            } catch (ServiceWrongLoginException e){
                request.setAttribute(REGISTRATION_FORM_LOGIN_PARAM, registrationFormLogin);
                request.setAttribute(REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM, registrationIdentificationNumberString);
                request.setAttribute(REGISTRATION_FORM_SERIES_PARAM, registrationFormSeries);
                request.setAttribute(REGISTRATION_FORM_SURNAME_PARAM, registrationFormSurname);
                request.setAttribute(REGISTRATION_FORM_LAST_NAME_PARAM, registrationFormLastName);
                request.setAttribute(REGISTRATION_FORM_NAME_PARAM, registrationFormName);
                request.setAttribute(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM, registrationBirthdayDateString);
                request.setAttribute(WRONG_LOGIN_REQUEST_ATTR, true);
            } catch (ServiceException e){
                request.setAttribute(REGISTRATION_FORM_LOGIN_PARAM, registrationFormLogin);
                request.setAttribute(REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM, registrationIdentificationNumberString);
                request.setAttribute(REGISTRATION_FORM_SERIES_PARAM, registrationFormSeries);
                request.setAttribute(REGISTRATION_FORM_SURNAME_PARAM, registrationFormSurname);
                request.setAttribute(REGISTRATION_FORM_LAST_NAME_PARAM, registrationFormLastName);
                request.setAttribute(REGISTRATION_FORM_NAME_PARAM, registrationFormName);
                request.setAttribute(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM, registrationBirthdayDateString);
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
