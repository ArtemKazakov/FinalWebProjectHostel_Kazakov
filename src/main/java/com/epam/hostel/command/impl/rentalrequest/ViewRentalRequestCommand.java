package com.epam.hostel.command.impl.rentalrequest;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
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
 * Services AJAX request to show rental request information.
 */
public class ViewRentalRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(ViewRentalRequestCommand.class);

    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String RENTAL_REQUEST_ID_PARAM = "rentalRequestId";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "Server error!";

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

        int id = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_ID_PARAM));

        String json;
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

        try {
            RentalRequestService rentalRequestService = ServiceFactory.getInstance().getRentalRequestService();
            RentalRequest rentalRequest = rentalRequestService.getRentalRequestById(id);
            json = gson.toJson(rentalRequest);
        } catch (ServiceException e) {
            logger.warn("While getting rental request error occurred", e);
            json = gson.toJson(SERVICE_ERROR_REQUEST_ATTR);
        }

        response.setContentType(JSON_FORMAT);
        response.getWriter().write(json);

    }
}
