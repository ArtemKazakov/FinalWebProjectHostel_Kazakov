package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public class MySQLRoomDAO implements RoomDAO {

    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String INSERT_ROOM_QUERY = "INSERT INTO `rooms` " +
            "(`id_room`, `seats_number`, `perday_cost`) " +
            "VALUES (?, ?, ?) ";

    private static final String UPDATE_ROOM_QUERY = "UPDATE `rooms` " +
            "SET `seats_number` = ?, `perday_cost` = ? " +
            "WHERE `id_room` = ? ";

    private static final String DELETE_ROOM_BY_ID_QUERY = "DELETE FROM `rooms` WHERE `id_room` = ? ";

    private static final String SELECT_ROOM_BY_NUMBER_QUERY = "SELECT * FROM `rooms` WHERE `id_room` = ? ";

    private static final String SELECT_ALL_ROOMS_QUERY = "SELECT * FROM `rooms`";

    private static final String SELECT_ROOMS_BY_SEATS_NUMBER_QUERY = "SELECT * FROM `rooms` WHERE `seats_number` = ? ";

    @Override
    public void insert(Room room) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_ROOM_QUERY);

            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getSeatsNumber());
            preparedStatement.setInt(3, room.getPerdayCost());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot insert room", e);
        } finally {
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }
    }

    @Override
    public void update(Room room) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ROOM_QUERY);

            preparedStatement.setInt(1, room.getSeatsNumber());
            preparedStatement.setInt(2, room.getPerdayCost());
            preparedStatement.setInt(3, room.getNumber());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot update discount", e);
        } finally {
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_ROOM_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot delete room", e);
        } finally {
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }
    }

    @Override
    public Room findByNumber(int number) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Room room = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ROOM_BY_NUMBER_QUERY);

            preparedStatement.setInt(1, number);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                room = new Room();
                room.setNumber(resultSet.getInt(1));
                room.setSeatsNumber(resultSet.getInt(2));
                room.setPerdayCost(resultSet.getInt(3));
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot get all rooms", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }

        return room;
    }

    @Override
    public List<Room> findAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Room> rooms = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(SELECT_ALL_ROOMS_QUERY);

            while (resultSet.next()) {
                Room room = new Room();
                room.setNumber(resultSet.getInt(1));
                room.setSeatsNumber(resultSet.getInt(2));
                room.setPerdayCost(resultSet.getInt(3));
                rooms.add(room);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot get all rooms", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }

        return rooms;
    }

    @Override
    public List<Room> findBySeatsNumber(int seatsNumber) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Room> rooms = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ROOMS_BY_SEATS_NUMBER_QUERY);

            preparedStatement.setInt(1, seatsNumber);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                room.setNumber(resultSet.getInt(1));
                room.setSeatsNumber(resultSet.getInt(2));
                room.setPerdayCost(resultSet.getInt(3));
                rooms.add(room);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot get rooms by seats number", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }

        return rooms;
    }

    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Can not close statement");
        }
    }


    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Can not close result set");
        }
    }
}
