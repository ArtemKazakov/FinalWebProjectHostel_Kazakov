package com.epam.hostel.command.impl.discount;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.service.DiscountService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services request from the adding discount form.
 */
public class AddDiscountCommand implements Command {
    private final static Logger logger = Logger.getLogger(AddDiscountCommand.class);

    private static final String REDIRECT_PAGE = "/Controller?command=viewUser";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String DISCOUNT_FORM_CLIENT_ID_PARAM = "discountFormClientId";
    private static final String DISCOUNT_FORM_VALUE_PARAM = "discountFormValue";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";
    private static final String CLIENT_ID_ATTRIBUTE = "clientId";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? false : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if(!userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        int administratorId = (session == null) ? -1 : (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);;
        if(administratorId == -1){
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        int clientId = CommandHelper.getInt(request.getParameter(DISCOUNT_FORM_CLIENT_ID_PARAM));
        int value = CommandHelper.getInt(request.getParameter(DISCOUNT_FORM_VALUE_PARAM));

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DiscountService discountService = serviceFactory.getDiscountService();
            discountService.addDiscount(clientId, value, administratorId);
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+clientId);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+clientId+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }

    }
}
