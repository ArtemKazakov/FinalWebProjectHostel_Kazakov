package com.epam.hostel.command.impl.general;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
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
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Services the request from the registration form.
 */
public class RegistrationCommand implements Command {

    private final static Logger logger = Logger.getRootLogger();

    private static final String REDIRECT_PAGE = "Controller?command=registrationPage&";

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

    private static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(REGISTRATION_FORM_LOGIN_PARAM);
        byte[] password = request.getParameter(REGISTRATION_FORM_PASSWORD_PARAM).getBytes(StandardCharsets.UTF_8);
        int identificationNumber = CommandHelper.getInt(request.getParameter(REGISTRATION_FORM_IDENTIFICATION_NUMBER_PARAM));
        String series = request.getParameter(REGISTRATION_FORM_SERIES_PARAM);
        String surname = request.getParameter(REGISTRATION_FORM_SURNAME_PARAM);
        String name = request.getParameter(REGISTRATION_FORM_NAME_PARAM);
        String patronymic = request.getParameter(REGISTRATION_FORM_PATRONYMIC_PARAM);
        SimpleDateFormat format = new SimpleDateFormat(BIRTHDAY_DATE_FORMAT);
        Date birthdayDate = null;
        if (request.getParameter(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM) != null) {
            try {
                birthdayDate = format.parse(request.getParameter(REGISTRATION_FORM_BIRTHDAY_DATE_PARAM));
            } catch (ParseException | NullPointerException e) {
                logger.error("Wrong birthday date for registration");
                response.sendRedirect(REDIRECT_PAGE);
                return;
            }
        }

        if (login != null && series != null && surname != null && name != null &&
                patronymic != null) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                siteService.registration(createUser(login, password),
                        createPassport(identificationNumber,
                                series, surname, name, patronymic,
                                birthdayDate));
                response.sendRedirect(REDIRECT_PAGE + REGISTRATION_SUCCESS_REQUEST_ATTR + EQ + true);
            } catch (ServiceWrongLoginException e) {
                logger.warn(e);
                response.sendRedirect(makeErrorRedirectString(WRONG_LOGIN_REQUEST_ATTR, login,
                        identificationNumber, series, surname,
                        patronymic, name, birthdayDate));
            } catch (ServiceWrongPasswordException e) {
                logger.warn(e);
                response.sendRedirect(makeErrorRedirectString(WRONG_PASSWORD_REQUEST_ATTR, login,
                        identificationNumber, series, surname,
                        patronymic, name, birthdayDate));
            } catch (ServiceException e) {
                logger.warn(e);
                response.sendRedirect(makeErrorRedirectString(SERVICE_ERROR_REQUEST_ATTR, login,
                        identificationNumber, series, surname,
                        patronymic, name, birthdayDate));
            }
        }

    }

    /**
     * Creates a {@link Passport} object.
     *
     * @param identificationNumber an identification number
     * @param series               a series
     * @param surname              a surname
     * @param name                 a name
     * @param patronymic           a patronymic
     * @param birthday             a birthday
     * @return a new passport
     */
    private Passport createPassport(int identificationNumber, String series, String surname, String name,
                                    String patronymic, Date birthday) {
        Passport passport = new Passport();
        passport.setIdentificationNumber(identificationNumber);
        passport.setSeries(series);
        passport.setSurname(surname);
        passport.setName(name);
        passport.setPatronymic(patronymic);
        passport.setBirthday(birthday);
        return passport;
    }

    /**
     * Creates a {@link User} object.
     *
     * @param login    a login
     * @param password a password
     * @return a new user
     */
    private User createUser(String login, byte[] password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }

    /**
     * Makes error redirect string.
     *
     * @param errorName            an error name
     * @param login                s login
     * @param identificationNumber an identification number
     * @param series               a series
     * @param surname              a surname
     * @param name                 a name
     * @param patronymic           a patronymic
     * @param birthday             a birthday
     * @return a redirect string
     */
    private String makeErrorRedirectString(String errorName, String login, int identificationNumber,
                                           String series, String surname, String name, String patronymic, Date birthday) {
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
