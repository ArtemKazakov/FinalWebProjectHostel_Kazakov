package com.epam.hostel.command.impl.user;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.DiscountService;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 22.11.2016.
 */
public class ViewUserCommand implements Command {

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=mainPage";
    private static final String CLIENT_ACCOUNT_PAGE = "WEB-INF/jsp/client-account.jsp";

    private static final String CLIENT_ID_ATTRIBUTE = "clientId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String USER_REQUEST_ATTR = "user";
    private static final String USER_DISCOUNTS_REQUEST_ATTR = "discounts";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? false : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if(!userRole) {
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        String idStr = request.getParameter(CLIENT_ID_ATTRIBUTE);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            User user = userService.getUserByIdAndRole(id, false);

            if(user != null){
                request.setAttribute(USER_REQUEST_ATTR, user);
                DiscountService discountService = serviceFactory.getDiscountService();

                List<Discount> discounts = discountService.getAllDiscountsByClientId(id);
                request.setAttribute(USER_DISCOUNTS_REQUEST_ATTR, discounts);
            }
            request.getRequestDispatcher(CLIENT_ACCOUNT_PAGE).forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            request.getRequestDispatcher(SESSION_TIMEOUT_PAGE).forward(request, response);
        }
    }
}
