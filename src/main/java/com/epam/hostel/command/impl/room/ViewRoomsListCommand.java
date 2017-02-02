package com.epam.hostel.command.impl.room;

import com.epam.hostel.bean.entity.PagedList;
import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.CommandHelper;
import com.epam.hostel.command.util.LanguageUtil;
import com.epam.hostel.command.util.QueryUtil;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Services request to the rooms page.
 */
public class ViewRoomsListCommand implements Command{

    private final static Logger logger = Logger.getLogger(ViewRoomsListCommand.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/rooms.jsp";

    private static final String SEATS_NUMBER_PARAM = "seatsNumber";
    private static final String PAGE_PARAM = "page";

    private static final String ROOMS_ATTRIBUTE = "rooms";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    private static final int AMOUNT = 10;
    private static final int DEFAULT_PAGE = 1;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        int seatsNumber = CommandHelper.getInt(request.getParameter(SEATS_NUMBER_PARAM));

        int page = CommandHelper.getInt(request.getParameter(PAGE_PARAM));
        if (page == -1){
            page = DEFAULT_PAGE;
        }

        try {
            RoomService roomService = ServiceFactory.getInstance().getRoomService();
            List<Room> rooms;
            int count = 0;

            if(seatsNumber == -1) {
                rooms = roomService.getAllRooms((page-1)*AMOUNT, AMOUNT);
                count = roomService.getRoomsCount();
            } else {
                rooms = roomService.getAllRoomsBySeatsNumber((page-1)*AMOUNT, AMOUNT, seatsNumber);
                count = roomService.getRoomsCountBySeatsNumber(seatsNumber);
            }

            PagedList<Room> pagedList = new PagedList<>();
            pagedList.setContent(rooms);
            pagedList.setLastPage((int)Math.ceil(count/(double)AMOUNT));
            pagedList.setCurrentPage(page);

            request.setAttribute(ROOMS_ATTRIBUTE, pagedList);
        } catch (ServiceException e){
            logger.warn(e);
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
