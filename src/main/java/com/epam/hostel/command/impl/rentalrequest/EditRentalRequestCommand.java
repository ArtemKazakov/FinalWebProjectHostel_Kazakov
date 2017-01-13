package com.epam.hostel.command.impl.rentalrequest;

import com.epam.hostel.command.Command;
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
 * Created by ASUS on 12.01.2017.
 */
public class EditRentalRequestCommand implements Command {
    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String REDIRECT_PAGE = "/Controller?command=viewRequestsList";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String RENTAL_REQUEST_ID_PARAM = "requestId";
    private static final String RENTAL_REQUEST_STATUS_PARAM = "requestStatus";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
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

        int administratorId = (session == null) ? -1 : (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);

        if(administratorId == -1){
            response.sendRedirect(MAIN_PAGE);
            return;
        }

        String idRentalRequestStr = request.getParameter(RENTAL_REQUEST_ID_PARAM);
        int idRentalRequest = -1;
        if (idRentalRequestStr != null) {
            try {
                idRentalRequest = Integer.parseInt(idRentalRequestStr);
            } catch (NumberFormatException e) {
                LOGGER.error("Wrong rental request id for editing rental request");
            }
        }

        String rentalRequestStatusStr = request.getParameter(RENTAL_REQUEST_STATUS_PARAM);
        boolean status = false;
        if (rentalRequestStatusStr != null) {
            try {
                if (Integer.parseInt(rentalRequestStatusStr) == 1){
                    status = true;
                } else if (Integer.parseInt(rentalRequestStatusStr) != 0){
                    response.sendRedirect(MAIN_PAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                LOGGER.error("Wrong client id for editing discount");
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RentalRequestService rentalRequestService = serviceFactory.getRentalRequestService();
            rentalRequestService.updateRentalRequest(idRentalRequest, status, administratorId);
            response.sendRedirect(REDIRECT_PAGE);
        } catch (ServiceException e) {
            response.sendRedirect(REDIRECT_PAGE + AMP + SERVICE_ERROR_REQUEST_ATTR + EQ + true);
        }

    }

}
