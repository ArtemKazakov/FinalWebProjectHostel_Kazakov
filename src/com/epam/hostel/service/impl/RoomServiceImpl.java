package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public class RoomServiceImpl implements RoomService{
    @Override
    public List<Room> getAllRooms() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RoomDAO dao = factory.getRoomDAO();
        List<Room> rooms = null;

        try{
            rooms = dao.selectAll();
            return rooms;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all rooms", e);
        }
    }
}
