package com.epam.hostel.service;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public interface RoomService {

    List<Room> getAllRooms() throws ServiceException;
}
