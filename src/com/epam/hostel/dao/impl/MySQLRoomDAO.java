package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public class MySQLRoomDAO implements RoomDAO {

    private static final String GET_ALL_ROOMS_QUERY = "SELECT * FROM `rooms`";

    @Override
    public List<Room> getAllRooms() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Room> rooms = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_ALL_ROOMS_QUERY);

            while(resultSet.next()){
                Room room = new Room();
                room.setNumber(resultSet.getInt(1));
                room.setSeatsNumber(resultSet.getInt(2));
                room.setPerdayCost(resultSet.getInt(3));
                rooms.add(room);
            }

            return rooms;
        } catch (InterruptedException | ConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e){
            throw new DAOException("SQL query error", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    throw new DAOException("Cannot free a connection from Connection Pool", e);
                }
            }
        }
    }
}
