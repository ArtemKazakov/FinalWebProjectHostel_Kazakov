package com.epam.hostel.command.impl.user;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ASUS on 10.11.2016.
 */
public class ViewUserCommand implements Command {

    private static final String MAIN_PAGE = "index.jsp";
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/user.jsp";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String USER_REQUEST_ATTR = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(USER_ID_SESSION_ATTRIBUTE);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserServiceService();
            User user = userService.getUserById(id);
            if(user != null){
                request.setAttribute(USER_REQUEST_ATTR, user);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
