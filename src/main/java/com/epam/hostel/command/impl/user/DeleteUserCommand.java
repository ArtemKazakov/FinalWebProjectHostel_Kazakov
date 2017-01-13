package com.epam.hostel.command.impl.user;

import com.epam.hostel.command.Command;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 06.01.2017.
 */
public class DeleteUserCommand implements Command{
    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String REDIRECT_PAGE = "/Controller?command=viewUsersList";
    private static final String ERROR_PAGE = "/Controller?command=viewUser&clientId=";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String CLIENT_ID_PARAM = "userId";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    private static final String AMP = "&";
    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        boolean userRole = (Boolean)session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);

        if(!userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        String idStr = request.getParameter(CLIENT_ID_PARAM);
        int id = -1;
        if(idStr != null){
            try{
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong id for deleting user");
            }
        }

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            userService.deleteUser(id);
            response.sendRedirect(REDIRECT_PAGE);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE+id+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }
    }
}
