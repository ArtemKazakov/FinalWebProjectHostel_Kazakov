package com.epam.hostel.command.impl.discount;

import com.epam.hostel.command.Command;
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
 * Created by ASUS on 18.11.2016.
 */
public class AddDiscountCommand implements Command {
    private final static Logger LOGGER = Logger.getRootLogger();

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

        String discountFormClientIdString = request.getParameter(DISCOUNT_FORM_CLIENT_ID_PARAM);
        int discountFormClientId = -1;
        if(discountFormClientIdString != null){
            try{
                discountFormClientId = Integer.parseInt(discountFormClientIdString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong client id for adding discount");
            }
        }
        String discountFormValueString = request.getParameter(DISCOUNT_FORM_VALUE_PARAM);
        int discountFormValue = -1;
        if(discountFormClientIdString != null){
            try{
                discountFormValue = Integer.parseInt(discountFormValueString);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong discount value for adding discount");
            }
        }

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DiscountService discountService = serviceFactory.getDiscountService();
            discountService.addDiscount(discountFormClientId, discountFormValue, administratorId);
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+discountFormClientId);
        } catch (ServiceException e) {
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+discountFormClientId+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }

    }
}
