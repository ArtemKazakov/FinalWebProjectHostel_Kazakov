package com.epam.hostel.command.impl.user;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.DiscountService;
import com.epam.hostel.service.RentalRequestService;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Services request to the user account page.
 */
public class UserAccountCommand implements Command {
    private final static Logger logger = Logger.getLogger(UserAccountCommand.class);

    private static final String MAIN_PAGE = "/Controller?command=mainPage";
    private static final String CLIENT_ACCOUNT_PAGE = "WEB-INF/jsp/client-account.jsp";
    private static final String ADMIN_ACCOUNT_PAGE = "WEB-INF/jsp/admin-account.jsp";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String USER_DISCOUNTS_REQUEST_ATTR = "discounts";
    private static final String USER_RENTAL_REQUESTS_REQUEST_ATTR = "rentalRequests";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String USER_REQUEST_ATTR = "user";

    private static final String PAGE_PARAM = "page";

    private static final int AMOUNT = 10;
    private static final int DEFAULT_PAGE = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        int page = CommandHelper.getInt(request.getParameter(PAGE_PARAM));
        if (page == -1){
            page = DEFAULT_PAGE;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            boolean userRole = (Boolean)session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
            int userId = (Integer)session.getAttribute(USER_ID_SESSION_ATTRIBUTE);

            User user = userService.getUserByIdAndRole(userId, userRole);

            if(user != null){
                request.setAttribute(USER_REQUEST_ATTR, user);
            }
            if (userRole){
                request.getRequestDispatcher(ADMIN_ACCOUNT_PAGE).forward(request, response);
            } else{
                DiscountService discountService = serviceFactory.getDiscountService();
                List<Discount> discounts = discountService.getAllDiscountsByClientId(userId);
                request.setAttribute(USER_DISCOUNTS_REQUEST_ATTR, discounts);

                RentalRequestService rentalRequestService = serviceFactory.getRentalRequestService();
                List<RentalRequest> rentalRequests = rentalRequestService.getAllRentalRequestsByClientId(userId);
                request.setAttribute(USER_RENTAL_REQUESTS_REQUEST_ATTR, rentalRequests);

                request.getRequestDispatcher(CLIENT_ACCOUNT_PAGE).forward(request, response);
            }
        } catch (ServiceException e) {
            logger.warn(e);
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
        }

    }
}
