package com.epam.hostel.command.impl.room;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public class ViewRoomsListCommand implements Command{

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/rooms.jsp";

    private static final String ROOMS_ATTRIBUTE = "rooms";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RoomService roomService = serviceFactory.getRoomService();
            List<Room> rooms = roomService.getAllRooms();
            request.setAttribute(ROOMS_ATTRIBUTE, rooms);
        } catch (ServiceException e){
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
