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
 * Services request from the editing user ban status room form.
 */
public class BanUserCommand implements Command {

    private final static Logger logger = Logger.getLogger(BanUserCommand.class);

    private static final String REDIRECT_PAGE = "/Controller?command=viewUser&clientId=";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String CLIENT_ID_PARAM = "userId";
    private static final String BAN_STATUS_PARAM = "status";

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

        boolean banned;
        int bannedNumber = CommandHelper.getInt(request.getParameter(BAN_STATUS_PARAM));
        if (bannedNumber == 1 || bannedNumber == 0){
            banned = bannedNumber != 0;
        } else {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            userService.banUser(id, banned);
            response.sendRedirect(REDIRECT_PAGE+id);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(REDIRECT_PAGE+id+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }
    }
}
