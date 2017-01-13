package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public interface RoomDAO {

    void insert(Room room) throws DAOException;

    void update(Room room) throws DAOException;

    void delete(int id) throws DAOException;

    Room findByNumber(int number) throws DAOException;

    List<Room> findAll() throws DAOException;

    List<Room> findBySeatsNumber(int seatsNumber) throws DAOException;

}
