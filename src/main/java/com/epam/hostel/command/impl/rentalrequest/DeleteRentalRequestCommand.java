package com.epam.hostel.command.impl.rentalrequest;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.service.RentalRequestService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services request from the deleting rental request form.
 */
public class DeleteRentalRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(DeleteRentalRequestCommand.class);

    private static final String REDIRECT_PAGE = "/Controller?command=viewRequestsList";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String RENTAL_REQUEST_ID_PARAM = "requestId";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    private static final String AMP = "&";
    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean userRole = (session == null) ? false : (Boolean) session.getAttribute(USER_ROLE_SESSION_ATTRIBUTE);
        if (!userRole) {
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        int idRentalRequest = CommandHelper.getInt(request.getParameter(RENTAL_REQUEST_ID_PARAM));

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RentalRequestService rentalRequestService = serviceFactory.getRentalRequestService();
            rentalRequestService.deleteRentalRequest(idRentalRequest);
            response.sendRedirect(REDIRECT_PAGE);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(REDIRECT_PAGE + AMP + SERVICE_ERROR_REQUEST_ATTR + EQ + true);
        }

    }
}
