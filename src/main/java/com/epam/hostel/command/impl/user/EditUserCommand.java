package com.epam.hostel.command.impl.user;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Services request from the editing user form.
 */
public class EditUserCommand implements Command {

    private final static Logger logger = Logger.getLogger(EditUserCommand.class);

    private static final String REDIRECT_PAGE = "/Controller?command=userAccount&userId=";
    private static final String REDIRECT_ADMIN_PAGE = "/Controller?command=viewUser&clientId=";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String USER_ID_PARAM = "userId";
    private static final String EDIT_FORM_LOGIN_PARAM = "editFormLogin";
    private static final String EDIT_FORM_PASSWORD_PARAM = "editFormPassword";
    private static final String EDIT_FORM_IDENTIFICATION_NUMBER_PARAM = "editFormIdentificationNumber";
    private static final String EDIT_FORM_SERIES_PARAM = "editFormSeries";
    private static final String EDIT_FORM_SURNAME_PARAM = "editFormSurname";
    private static final String EDIT_FORM_NAME_PARAM = "editFormName";
    private static final String EDIT_FORM_PATRONYMIC_PARAM = "editFormPatronymic";
    private static final String EDIT_FORM_BIRTHDAY_DATE_PARAM = "editFormBirthdayDate";

    private static final String EDIT_SUCCESS_REQUEST_ATTR = "editSuccess";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";

    private static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = CommandHelper.getInt(request.getParameter(USER_ID_PARAM));

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        boolean userRole = (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        int userId = (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);

        if (!userRole && (userId != id)) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        String login = request.getParameter(EDIT_FORM_LOGIN_PARAM);
        byte[] password = request.getParameter(EDIT_FORM_PASSWORD_PARAM).getBytes(StandardCharsets.UTF_8);
        int identificationNumber = CommandHelper.getInt(request.getParameter(EDIT_FORM_IDENTIFICATION_NUMBER_PARAM));
        String series = request.getParameter(EDIT_FORM_SERIES_PARAM);
        String surname = request.getParameter(EDIT_FORM_SURNAME_PARAM);
        String name = request.getParameter(EDIT_FORM_NAME_PARAM);
        String patronymic = request.getParameter(EDIT_FORM_PATRONYMIC_PARAM);
        SimpleDateFormat format = new SimpleDateFormat(BIRTHDAY_DATE_FORMAT);
        Date birthdayDate = null;
        if (request.getParameter(EDIT_FORM_BIRTHDAY_DATE_PARAM) != null) {
            try {
                birthdayDate = format.parse(request.getParameter(EDIT_FORM_BIRTHDAY_DATE_PARAM));
            } catch (ParseException | NullPointerException e) {
                logger.error("Wrong birthday date for editing user");
                response.sendRedirect(MAIN_PAGE);
                return;
            }
        }

        String redirectPage = userRole ? REDIRECT_ADMIN_PAGE : REDIRECT_PAGE;

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            userService.updateUser(createUser(id, login, password),
                    createPassport(identificationNumber,
                            series, surname, name, patronymic,
                            birthdayDate));
            response.sendRedirect(redirectPage + id + AMP + EDIT_SUCCESS_REQUEST_ATTR + EQ + true);
        } catch (ServiceWrongLoginException e) {
            logger.warn(e);
            response.sendRedirect(redirectPage + id + AMP + WRONG_LOGIN_REQUEST_ATTR + EQ + true);
        } catch (ServiceWrongPasswordException e) {
            logger.warn(e);
            response.sendRedirect(redirectPage + id + AMP + WRONG_PASSWORD_REQUEST_ATTR + EQ + true);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(redirectPage + id + AMP + SERVICE_ERROR_REQUEST_ATTR + EQ + true);
        }

    }

    /**
     * Creates a {@link User} object.
     *
     * @param id       an id
     * @param login    a login
     * @param password a password
     * @return a new user
     */
    private User createUser(int id, String login, byte[] password) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        return user;
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
    private Passport createPassport(int identificationNumber, String series,
                                    String surname, String name, String patronymic, Date birthday) {
        Passport passport = new Passport();
        passport.setIdentificationNumber(identificationNumber);
        passport.setSeries(series);
        passport.setSurname(surname);
        passport.setName(name);
        passport.setPatronymic(patronymic);
        passport.setBirthday(birthday);
        return passport;
    }

}
