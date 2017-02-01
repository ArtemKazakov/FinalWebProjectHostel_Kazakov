package com.epam.hostel.command.impl.room;


import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.command.Command;
import com.epam.hostel.service.RoomService;
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
import java.util.Collections;
import java.util.List;

public class GetRoomsCommand implements Command {

    private final static Logger logger = Logger.getLogger(GetRoomsCommand.class);

    private static final String MAIN_PAGE = "/Controller?command=mainPage";

    private static final String PAGE_PARAM = "page";
    private static final int AMOUNT = 10;

    private static final String USER_ROLE_SESSION_ATTRIBUTE = "userRole";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "Server error!";

    private static final String JSON_FORMAT = "application/json";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pageStr = request.getParameter(PAGE_PARAM);
        int page = -1;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                logger.error("Wrong page for getting rooms");
            }
        }

        String json;
        List<Room> rooms;
        Gson gson = new GsonBuilder().create();
        try {
            RoomService roomService = ServiceFactory.getInstance().getRoomService();
            rooms = roomService.getAllRooms((page-1)*AMOUNT, AMOUNT);
        } catch (ServiceException e) {
            logger.warn("While getting rental request error occurred", e);
            rooms = Collections.emptyList();
        }

        json = gson.toJson(rooms);
        response.setContentType(JSON_FORMAT);
        response.getWriter().write(json);
    }
}
