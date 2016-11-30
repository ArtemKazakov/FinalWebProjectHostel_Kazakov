package com.epam.hostel.command.impl.user;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS on 10.11.2016.
 */
public class EditUserCommand implements Command {

    private static final String REDIRECT_PAGE = "/Controller?command=userAccount&userId=";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";

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
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(USER_ID_SESSION_ATTRIBUTE);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        String editFormLogin = request.getParameter(EDIT_FORM_LOGIN_PARAM);
        String editFormPassword = request.getParameter(EDIT_FORM_PASSWORD_PARAM);
        String editFormIdentificationNumberString = request.getParameter(EDIT_FORM_IDENTIFICATION_NUMBER_PARAM);
        int editFormIdentificationNumber = -1;
        if(editFormIdentificationNumberString != null){
            try{
                editFormIdentificationNumber = Integer.parseInt(editFormIdentificationNumberString);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        String editFormSeries = request.getParameter(EDIT_FORM_SERIES_PARAM);
        String editFormSurname = request.getParameter(EDIT_FORM_SURNAME_PARAM);
        String editFormName = request.getParameter(EDIT_FORM_NAME_PARAM);
        String editFormPatronymic = request.getParameter(EDIT_FORM_PATRONYMIC_PARAM);
        String editFormBirthdayDateString = request.getParameter(EDIT_FORM_BIRTHDAY_DATE_PARAM);
        SimpleDateFormat format = new SimpleDateFormat(BIRTHDAY_DATE_FORMAT);
        Date editFormBirthdayDate = null;
        if (editFormBirthdayDateString != null) {
            try {
                editFormBirthdayDate = format.parse(request.getParameter(EDIT_FORM_BIRTHDAY_DATE_PARAM));
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (editFormLogin != null && editFormPassword != null &&
                editFormSeries != null && editFormSurname != null && editFormName != null &&
                editFormPatronymic != null) {

            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                UserService userService = serviceFactory.getUserService();
                userService.updateUser(id, editFormLogin, editFormPassword, editFormIdentificationNumber,
                        editFormSeries, editFormSurname, editFormName, editFormPatronymic,
                        editFormBirthdayDate);
                response.sendRedirect(REDIRECT_PAGE+id+AMP+EDIT_SUCCESS_REQUEST_ATTR+EQ+true);
            } catch (ServiceWrongLoginException e){
                response.sendRedirect(REDIRECT_PAGE+id+AMP+WRONG_LOGIN_REQUEST_ATTR+EQ+true);
            } catch (ServiceWrongPasswordException e){
                response.sendRedirect(REDIRECT_PAGE+id+AMP+WRONG_PASSWORD_REQUEST_ATTR+EQ+true);
            } catch (ServiceException e){
                response.sendRedirect(REDIRECT_PAGE+id+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
            }
        }
    }

}
