package com.epam.hostel.service;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * Provides a business-logic with the {@link Room} entity and relate with it.
 */
public interface RoomService {

    /**
     * Creates a room in a data source
     *
     * @param number      a number of the room
     * @param seatsNumber a seats number of the room
     * @param perdayCost  a per day cost of the room
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void addRoom(int number, int seatsNumber, int perdayCost) throws ServiceException;

    /**
     * Updates a room in a data source
     *
     * @param originalNumber an original number of the room
     * @param number         a new number of the room
     * @param seatsNumber    a seats number of the room
     * @param perdayCost     a per day cost of the room
     * @throws ServiceException n case of error occurred with a data source
     *                          or validation of data
     */
    void updateRoom(int originalNumber, int number, int seatsNumber, int perdayCost) throws ServiceException;

    /**
     * Deletes a room from a data source by number
     *
     * @param number a number of room for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void deleteRoom(int number) throws ServiceException;

    /**
     * Return all rooms from a data source
     *
     * @param start  the number from which accounts will be returned
     * @param amount of rooms
     * @return a {@link List} of rooms
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<Room> getAllRooms(int start, int amount) throws ServiceException;

    /**
     * Return all rooms from a data source by seats number
     *
     * @param start       the number from which accounts will be returned
     * @param amount      of rooms
     * @param seatsNumber a seats number of finding rooms
     * @return a {@link List} of rooms
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<Room> getAllRoomsBySeatsNumber(int start, int amount, int seatsNumber) throws ServiceException;

    /**
     * Return all rooms from a data source by check in date,
     * days stay number and seats number
     *
     * @param checkInDate    a check in date
     * @param daysStayNumber a days stay number
     * @param seatsNumber    a seats number of finding rooms
     * @return a {@link List} of rooms
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<Room> getAllSuitableRooms(Date checkInDate, int daysStayNumber, int seatsNumber) throws ServiceException;

    /**
     * Returns number of rooms in data source.
     *
     * @return amount of rooms
     * @throws ServiceException if error occurred with data source
    */
    int getRoomsCount() throws ServiceException;

    /**
     * Returns number of rooms in data source.
     *
     * @param seatsNumber a seats number of finding rooms
     * @return amount of rooms
     * @throws ServiceException if error occurred with data source
     */
    int getRoomsCountBySeatsNumber(int seatsNumber) throws ServiceException;

}
