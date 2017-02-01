package com.epam.hostel.command.impl.room;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceExistingRoomNumberException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services request from the editing room form.
 */
public class EditRoomCommand implements Command {
    private final static Logger logger = Logger.getLogger(EditRoomCommand.class);

    private static final String REDIRECT_PAGE = "/Controller?command=viewRoomsList";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String EDIT_ROOM_FORM_NUMBER_PARAM = "editFormRoomNumber";
    private static final String EDIT_ROOM_FORM_ORIGINAL_NUMBER_PARAM = "editFormOriginalRoomNumber";
    private static final String EDIT_ROOM_FORM_SEATS_NUMBER_PARAM = "editFormRoomSeatsNumber";
    private static final String EDIT_ROOM_FORM_PERDAY_COST_PARAM = "editFormRoomPerdayCost";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String EDIT_ROOM_SUCCESS_REQUEST_ATTR = "editRoomSuccess";
    private static final String EXISTING_ROOM_ERROR_REQUEST_ATTR = "existingRoomError";
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

        int number = CommandHelper.getInt(request.getParameter(EDIT_ROOM_FORM_NUMBER_PARAM));
        int originalNumber = CommandHelper.getInt(request.getParameter(EDIT_ROOM_FORM_ORIGINAL_NUMBER_PARAM));
        int seatsNumber = CommandHelper.getInt(request.getParameter(EDIT_ROOM_FORM_SEATS_NUMBER_PARAM));
        int perdayCost = CommandHelper.getInt(request.getParameter(EDIT_ROOM_FORM_PERDAY_COST_PARAM));

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RoomService roomService = serviceFactory.getRoomService();
            roomService.updateRoom(originalNumber, number, seatsNumber, perdayCost);
            response.sendRedirect(REDIRECT_PAGE+AMP+EDIT_ROOM_SUCCESS_REQUEST_ATTR+EQ+true);
        } catch (ServiceExistingRoomNumberException e) {
            logger.warn(e);
            response.sendRedirect(REDIRECT_PAGE+AMP+EXISTING_ROOM_ERROR_REQUEST_ATTR+EQ+true);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(REDIRECT_PAGE+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }

    }
}
