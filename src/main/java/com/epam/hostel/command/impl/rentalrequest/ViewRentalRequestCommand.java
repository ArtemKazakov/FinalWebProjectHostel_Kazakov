package com.epam.hostel.command.impl.rentalrequest;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.command.Command;
import com.epam.hostel.service.RentalRequestService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 09.01.2017.
 */
public class ViewRentalRequestCommand implements Command {
    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String RENTAL_REQUEST_ID_PARAM = "rentalRequestId";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String RENTAL_REQ_REQUEST_ATTR = "rentalRequest";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String JSON_FORMAT = "application/json";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? false : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if (!userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        String idStr = request.getParameter(RENTAL_REQUEST_ID_PARAM);
        int id = -1;
        if (idStr != null) {
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                LOGGER.error("Wrong id for view rental request");
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RentalRequestService rentalRequestService = serviceFactory.getRentalRequestService();
            RentalRequest rentalRequest = rentalRequestService.getRentalRequestById(id);
            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
            String json = gson.toJson(rentalRequest);
            // request.setAttribute(RENTAL_REQ_REQUEST_ATTR, rentalRequest);

            response.setContentType(JSON_FORMAT);
            response.getWriter().write(json);

        } catch (ServiceException e) {
            //     request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

    }
}
