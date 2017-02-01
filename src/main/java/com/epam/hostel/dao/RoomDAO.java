package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link Room} entity.
 */
public interface RoomDAO {

    /**
     * Inserts a new room into a data source.
     *
     * @param room a room object for insertion
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void insert(Room room) throws DAOException;

    /**
     * Updates a room in a data source.
     *
     * @param room a room object for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void update(Room room) throws DAOException;

    /**
     * Deletes a room from a data source by id.
     *
     * @param id an id of deleting room
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void delete(int id) throws DAOException;

    /**
     * Gives a room from a data source by number.
     *
     * @param number an id of a desired room
     * @return a room object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    Room findByNumber(int number) throws DAOException;

    /**
     * Gives a list of all rooms from a data source.
     *
     * @return a {@link List} of rooms
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<Room> findAll(int start, int amount) throws DAOException;

    /**
     * Gives a list of rooms from a data source by seats number.
     *
     * @param seatsNumber a seats number of desired rooms
     * @return a {@link List} of rooms containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<Room> findBySeatsNumber(int seatsNumber) throws DAOException;

}
