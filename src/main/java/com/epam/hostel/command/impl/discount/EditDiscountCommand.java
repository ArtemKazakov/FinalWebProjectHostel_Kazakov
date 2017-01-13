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
 * Created by ASUS on 06.01.2017.
 */
public class EditDiscountCommand implements Command {
    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String REDIRECT_PAGE = "/Controller?command=viewUser";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String CLIENT_ID_PARAM = "userId";
    private static final String DISCOUNT_ID_PARAM = "discountId";
    private static final String DISCOUNT_VALUE_PARAM = "value";

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

        String idDiscountStr = request.getParameter(DISCOUNT_ID_PARAM);
        int idDiscount = -1;
        if(idDiscountStr != null){
            try{
                idDiscount = Integer.parseInt(idDiscountStr);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong discount id for editing discount");
            }
        }

        String idClientStr = request.getParameter(CLIENT_ID_PARAM);
        int idClient = -1;
        if(idDiscountStr != null){
            try{
                idClient = Integer.parseInt(idClientStr);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong client id for editing discount");
            }
        }

        String valueStr = request.getParameter(DISCOUNT_VALUE_PARAM);
        int value = -1;
        if(idDiscountStr != null){
            try{
                value = Integer.parseInt(valueStr);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong client id for editing discount");
            }
        }

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            DiscountService discountService = serviceFactory.getDiscountService();
            discountService.updateDiscount(idDiscount, idClient, value);
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+idClient);
        } catch (ServiceException e) {
            response.sendRedirect(REDIRECT_PAGE+AMP+CLIENT_ID_ATTRIBUTE+EQ+idClient+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }

    }
}
