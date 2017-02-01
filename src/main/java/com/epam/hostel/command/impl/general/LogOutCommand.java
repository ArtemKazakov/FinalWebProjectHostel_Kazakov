package com.epam.hostel.command.impl.general;

import com.epam.hostel.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request to log out from the system.
 */
public class LogOutCommand implements Command {

    private static final String JSP_PAGE_PATH = "/Controller?command=mainPage";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute(USER_ID_SESSION_ATTRIBUTE);
            session.removeAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        }
        response.sendRedirect(JSP_PAGE_PATH);
    }
}
