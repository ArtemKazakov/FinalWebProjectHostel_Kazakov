package com.epam.hostel.command.impl.user;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
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
 * Services request from the deleting user form.
 */
public class DeleteUserCommand implements Command{
    private final static Logger logger = Logger.getLogger(DeleteUserCommand.class);

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

        int id = CommandHelper.getInt(request.getParameter(CLIENT_ID_PARAM));

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            userService.deleteUser(id);
            response.sendRedirect(REDIRECT_PAGE);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(ERROR_PAGE+id+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }
    }
}
