package com.epam.hostel.command.impl.general;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.SiteService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

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

    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/registration.jsp";
    private static final String REDIRECT_PAGE = "Controller?command=registration&";

    private static final String REGISTRATION_FORM_LOGIN_PARAM = "registrationFormLogin";
    private static final String REGISTRATION_FORM_PASSWORD_PARAM = "registrationFormPassword";
    private static final String REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM = "registrationFormIdentificationNumber";
    private static final String REGISTRATION_FORM_SERIES_PARAM = "registrationFormSeries";
    private static final String REGISTRATION_FORM_SURNAME_PARAM = "registrationFormSurname";
    private static final String REGISTRATION_FORM_NAME_PARAM = "registrationFormName";
    private static final String REGISTRATION_FORM_PATRONYMIC_PARAM = "registrationFormPatronymic";
    private static final String REGISTRATION_FORM_BIRTHDAY_DATE_PARAM = "registrationFormBirthdayDate";

    private static final String REGISTRATION_SUCCESS_REQUEST_ATTR = "registrationSuccess";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String registrationFormLogin = request.getParameter(REGISTRATION_FORM_LOGIN_PARAM);
        String registrationFormPassword = request.getParameter(REGISTRATION_FORM_PASSWORD_PARAM);
        String registrationFormIdentificationNumberString = request.getParameter(REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM);
        int registrationFormIdentificationNumber = -1;
        if(registrationFormIdentificationNumberString != null){
            try{
                registrationFormIdentificationNumber = Integer.parseInt(registrationFormIdentificationNumberString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong passport id for registration");
            }
        }
        String registrationFormSeries = request.getParameter(REGISTRATION_FORM_SERIES_PARAM);
        String registrationFormSurname = request.getParameter(REGISTRATION_FORM_SURNAME_PARAM);
        String registrationFormName = request.getParameter(REGISTRATION_FORM_NAME_PARAM);
        String registrationFormPatronymic = request.getParameter(REGISTRATION_FORM_PATRONYMIC_PARAM);
        String registrationFormBirthdayDateString = request.getParameter(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM);
        SimpleDateFormat format = new SimpleDateFormat(BIRTHDAY_DATE_FORMAT);
        Date registrationFormBirthdayDate = null;
        if (registrationFormBirthdayDateString != null) {
            try {
                registrationFormBirthdayDate = format.parse(registrationFormBirthdayDateString);
            } catch (ParseException | NullPointerException e) {
                LOGGER.error("Wrong birthday date for registration");
            }
        }

        if (registrationFormLogin != null && registrationFormPassword != null &&
                registrationFormSeries != null && registrationFormSurname != null && registrationFormName != null &&
                registrationFormPatronymic != null) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                siteService.registration(registrationFormLogin, registrationFormPassword, registrationFormIdentificationNumber,
                        registrationFormSeries, registrationFormSurname, registrationFormName, registrationFormPatronymic,
                        registrationFormBirthdayDate);
                response.sendRedirect(REDIRECT_PAGE+REGISTRATION_SUCCESS_REQUEST_ATTR+EQ+true);
            } catch (ServiceWrongLoginException e){
                response.sendRedirect(makeErrorRedirectString(WRONG_LOGIN_REQUEST_ATTR, registrationFormLogin,
                        registrationFormIdentificationNumber, registrationFormSeries, registrationFormSurname,
                        registrationFormPatronymic, registrationFormName, registrationFormBirthdayDate));
            } catch (ServiceWrongPasswordException e){
                response.sendRedirect(makeErrorRedirectString(WRONG_PASSWORD_REQUEST_ATTR, registrationFormLogin,
                        registrationFormIdentificationNumber, registrationFormSeries, registrationFormSurname,
                        registrationFormPatronymic, registrationFormName, registrationFormBirthdayDate));
            } catch (ServiceException e){
                response.sendRedirect(makeErrorRedirectString(SERVICE_ERROR_REQUEST_ATTR, registrationFormLogin,
                        registrationFormIdentificationNumber, registrationFormSeries, registrationFormSurname,
                        registrationFormPatronymic, registrationFormName, registrationFormBirthdayDate));
            }
        }

        else {
            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }
    }

    private String makeErrorRedirectString(String errorName, String login, int identificationNumber,
                                           String series, String surname, String name, String patronymic, Date birthday){
        StringBuilder parameters = new StringBuilder();
        parameters.append(REDIRECT_PAGE);
        parameters.append(errorName);
        parameters.append(EQ);
        parameters.append(true);
        parameters.append(AMP);
        parameters.append(REGISTRATION_FORM_LOGIN_PARAM);
        parameters.append(EQ);
        parameters.append(login);
        parameters.append(AMP);
        parameters.append(REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM);
        parameters.append(EQ);
        parameters.append(identificationNumber);
        parameters.append(AMP);
        parameters.append(REGISTRATION_FORM_SERIES_PARAM);
        parameters.append(EQ);
        parameters.append(series);
        parameters.append(AMP);
        parameters.append(REGISTRATION_FORM_SURNAME_PARAM);
        parameters.append(EQ);
        parameters.append(surname);
        parameters.append(AMP);
        parameters.append(REGISTRATION_FORM_PATRONYMIC_PARAM);
        parameters.append(EQ);
        parameters.append(patronymic);
        parameters.append(AMP);
        parameters.append(REGISTRATION_FORM_NAME_PARAM);
        parameters.append(EQ);
        parameters.append(name);
        parameters.append(AMP);
        parameters.append(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM);
        parameters.append(EQ);
        parameters.append(birthday);

        return parameters.toString();
    }
}
