package com.epam.hostel.command.impl.general;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.service.SiteService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 29.10.2016.
 */
public class LogInCommand implements Command {

    private static final String JSP_PAGE_PATH = "index.jsp";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";

    private static final String LOGIN_FORM_LOGIN_PARAM = "logInFormLogin";
    private static final String LOGIN_FORM_PASSWORD_PARAM = "logInFormPassword";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String logInFormLogin = request.getParameter(LOGIN_FORM_LOGIN_PARAM);
        String logInFormPassword = request.getParameter(LOGIN_FORM_PASSWORD_PARAM);

        if(logInFormLogin != null && logInFormPassword != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                User user = siteService.logIn(logInFormLogin, logInFormPassword);
                HttpSession session = request.getSession(true);
                session.setAttribute(USER_ID_SESSION_ATTRIBUTE, user.getId());
                response.sendRedirect(JSP_PAGE_PATH);
            } catch (ServiceWrongLoginException e){
                request.setAttribute(WRONG_LOGIN_REQUEST_ATTR, true);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (ServiceWrongPasswordException e){
                request.setAttribute(WRONG_PASSWORD_REQUEST_ATTR, true);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (ServiceException e){
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            }
        }


    }
}
