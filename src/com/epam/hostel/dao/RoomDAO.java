package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public interface RoomDAO {

    List<Room> findAll() throws DAOException;

    List<Room> findBySeatsNumber(int seatsNumber) throws DAOException;


}
