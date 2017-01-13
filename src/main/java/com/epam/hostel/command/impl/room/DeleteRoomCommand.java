package com.epam.hostel.command.impl.room;

import com.epam.hostel.command.Command;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 08.01.2017.
 */
public class DeleteRoomCommand implements Command {
    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String REDIRECT_PAGE = "/Controller?command=viewRoomsList";
    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String DELETE_ROOM_FORM_NUMBER_PARAM = "deleteFormRoomNumber";

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String DELETE_ROOM_SUCCESS_REQUEST_ATTR = "deleteRoomSuccess";
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

        String numberStr = request.getParameter(DELETE_ROOM_FORM_NUMBER_PARAM);
        int number = -1;
        if(numberStr != null){
            try{
                number = Integer.parseInt(numberStr);
            } catch (NumberFormatException e){
                LOGGER.error("Wrong number for adding room");
            }
        }

        try{
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RoomService roomService = serviceFactory.getRoomService();
            roomService.deleteRoom(number);
            response.sendRedirect(REDIRECT_PAGE+AMP+DELETE_ROOM_SUCCESS_REQUEST_ATTR+EQ+true);
        }  catch (ServiceException e) {
            response.sendRedirect(REDIRECT_PAGE+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
        }

    }
}
