package com.epam.hostel.service;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public interface RoomService {

    void addRoom(int number, int seatsNumber, int perdayCost) throws ServiceException;

    void updateRoom(int originalNumber, int number, int seatsNumber, int perdayCost) throws ServiceException;

    void deleteRoom(int number) throws ServiceException;

    List<Room> getAllRooms() throws ServiceException;

    List<Room> getAllRoomsBySeatsNumber(int seatsNumber) throws ServiceException;

    List<Room> getAllSuitableRooms(Date checkInDate, int daysStayNumber, int seatsNumber) throws ServiceException;

}
