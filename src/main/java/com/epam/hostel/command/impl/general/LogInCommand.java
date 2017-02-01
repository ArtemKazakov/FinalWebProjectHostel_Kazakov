package com.epam.hostel.command.impl.general;

import com.epam.hostel.bean.entity.User;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Services the request from the log in form.
 */
public class LogInCommand implements Command {
    private final static Logger logger = Logger.getLogger(LogInCommand.class);

    private static final String REDIRECT_PAGE = "/Controller?command=mainPage";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String LOGIN_FORM_LOGIN_PARAM = "logInFormLogin";
    private static final String LOGIN_FORM_PASSWORD_PARAM = "logInFormPassword";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String USER_BANNED_REQUEST_ATTR = "userBanned";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String login = request.getParameter(LOGIN_FORM_LOGIN_PARAM);
        byte[] password = request.getParameter(LOGIN_FORM_PASSWORD_PARAM).getBytes(StandardCharsets.UTF_8);

        if(login != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                User user = siteService.logIn(login, password);
                if (user.isBanned()){
                    response.sendRedirect(REDIRECT_PAGE+AMP+USER_BANNED_REQUEST_ATTR+EQ+true);
                    return;
                }
                HttpSession session = request.getSession(true);
                session.setAttribute(USER_ID_SESSION_ATTRIBUTE, user.getId());
                session.setAttribute(USER_ROLE_SESSION_ATTRIBUTE, user.isAdmin());
                response.sendRedirect(REDIRECT_PAGE);
            } catch (ServiceWrongLoginException e){
                logger.warn(e);
                response.sendRedirect(REDIRECT_PAGE+AMP+WRONG_LOGIN_REQUEST_ATTR+EQ+true);
            } catch (ServiceWrongPasswordException e){
                logger.warn(e);
                response.sendRedirect(REDIRECT_PAGE+AMP+WRONG_PASSWORD_REQUEST_ATTR+EQ+true);
            } catch (ServiceException e){
                logger.warn(e);
                response.sendRedirect(REDIRECT_PAGE+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
            }
        }
        else{
            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

            request.getRequestDispatcher(REDIRECT_PAGE).forward(request, response);
        }

    }
}
