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
 * Services request from the deleting discount form.
 */
public class DeleteDiscountCommand implements Command {
    private final static Logger logger = Logger.getLogger(DeleteDiscountCommand.class);

    private static final String REDIRECT_PAGE = "/Controller?command=viewUser";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";
    private static final String CLIENT_ID_ATTRIBUTE = "clientId";

    private static final String CLIENT_ID_PARAM = "userId";
    private static final String DISCOUNT_ID_PARAM = "discountId";

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

        int idDiscount = CommandHelper.getInt(request.getParameter(DISCOUNT_ID_PARAM));
        int idClient = CommandHelper.getInt(request.getParameter(CLIENT_ID_PARAM));

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DiscountService discountService = serviceFactory.getDiscountService();
            discountService.deleteDiscount(idDiscount);
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+idClient);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+idClient+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }
    }
}
