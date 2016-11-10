package com.epam.hostel.command.impl.user;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.service.SiteService;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS on 10.11.2016.
 */
public class EditUserCommand implements Command {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/user.jsp";
    private static final String MAIN_PAGE = "index.jsp";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";

    private static final String EDIT_FORM_LOGIN_PARAM = "editFormLogin";
    private static final String EDIT_FORM_PASSWORD_PARAM = "editFormPassword";
    private static final String EDIT_FORM_IDENTIFICATION_NUMBER_PARAM = "editFormIdentificationNumber";
    private static final String EDIT_FORM_SERIES_PARAM = "editFormSeries";
    private static final String EDIT_FORM_SURNAME_PARAM = "editFormSurname";
    private static final String EDIT_FORM_NAME_PARAM = "editFormName";
    private static final String EDIT_FORM_LAST_NAME_PARAM = "editFormLastName";
    private static final String EDIT_FORM_BIRTHDAY_DATE_PARAM = "editFormBirthdayDate";

    private static final String EDIT_SUCCESS_REQUEST_ATTR = "editSuccess";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";
    private static final String USER_REQUEST_ATTR = "user";

    private static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(USER_ID_SESSION_ATTRIBUTE);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
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
        String editFormLastName = request.getParameter(EDIT_FORM_LAST_NAME_PARAM);
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
                editFormLastName != null) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                UserService userService = serviceFactory.getUserServiceService();
                userService.updateUser(id, editFormLogin, editFormPassword, editFormIdentificationNumber,
                        editFormSeries, editFormSurname, editFormName, editFormLastName,
                        editFormBirthdayDate);
                request.setAttribute(EDIT_SUCCESS_REQUEST_ATTR, true);
                User user = userService.getUserById(id);
                if(user != null){
                    request.setAttribute(USER_REQUEST_ATTR, user);
                }
            } catch (ServiceWrongLoginException e){
                request.setAttribute(EDIT_FORM_LOGIN_PARAM, editFormLogin);
                request.setAttribute(EDIT_FORM_IDENTIFICATION_NUMBER_PARAM, editFormIdentificationNumberString);
                request.setAttribute(EDIT_FORM_SERIES_PARAM, editFormSeries);
                request.setAttribute(EDIT_FORM_SURNAME_PARAM, editFormSurname);
                request.setAttribute(EDIT_FORM_LAST_NAME_PARAM, editFormLastName);
                request.setAttribute(EDIT_FORM_NAME_PARAM, editFormName);
                request.setAttribute(EDIT_FORM_BIRTHDAY_DATE_PARAM, editFormBirthdayDateString);
                request.setAttribute(WRONG_LOGIN_REQUEST_ATTR, true);
            } catch (ServiceWrongPasswordException e){
                request.setAttribute(EDIT_FORM_LOGIN_PARAM, editFormLogin);
                request.setAttribute(EDIT_FORM_IDENTIFICATION_NUMBER_PARAM, editFormIdentificationNumberString);
                request.setAttribute(EDIT_FORM_SERIES_PARAM, editFormSeries);
                request.setAttribute(EDIT_FORM_SURNAME_PARAM, editFormSurname);
                request.setAttribute(EDIT_FORM_LAST_NAME_PARAM, editFormLastName);
                request.setAttribute(EDIT_FORM_NAME_PARAM, editFormName);
                request.setAttribute(EDIT_FORM_BIRTHDAY_DATE_PARAM, editFormBirthdayDateString);
                request.setAttribute(WRONG_PASSWORD_REQUEST_ATTR, true);
            } catch (ServiceException e){
                request.setAttribute(EDIT_FORM_LOGIN_PARAM, editFormLogin);
                request.setAttribute(EDIT_FORM_IDENTIFICATION_NUMBER_PARAM, editFormIdentificationNumberString);
                request.setAttribute(EDIT_FORM_SERIES_PARAM, editFormSeries);
                request.setAttribute(EDIT_FORM_SURNAME_PARAM, editFormSurname);
                request.setAttribute(EDIT_FORM_LAST_NAME_PARAM, editFormLastName);
                request.setAttribute(EDIT_FORM_NAME_PARAM, editFormName);
                request.setAttribute(EDIT_FORM_BIRTHDAY_DATE_PARAM, editFormBirthdayDateString);
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
