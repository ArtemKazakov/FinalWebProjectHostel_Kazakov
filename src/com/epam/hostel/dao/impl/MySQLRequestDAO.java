package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Request;
import com.epam.hostel.dao.RequestDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 16.11.2016.
 */
public class MySQLRequestDAO implements RequestDAO {
    Logger LOGGER = Logger.getRootLogger();

    private static final String INSERT_REQUEST_QUERY = "INSERT INTO `requests` " +
            "(`id_client`, `id_administrator`, `seats_number`, `checkin_date`, `days_stay`, `request_type`) " +
            "VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE_REQUEST_QUERY = "UPDATE `requests` " +
            "SET `id_client` = ?, `id_administrator` = ?, `seats_number` = ?, `checkin_date` = ?, " +
            "`days_stay` = ?, `request_type` = ?, `payment` = ?, `request_status` = ? " +
            "WHERE `id_request` = ? ";

    private static final String SELECT_REQUESTS_BY_CLIENT_ID_QUERY = "SELECT * FROM `requests` WHERE `id_client` = ? ";

    private static final String SELECT_REQUESTS_BY_ADMINISTRATOR_ID_QUERY = "SELECT * FROM `requests` " +
            "WHERE `id_administrator` = ? ";

    private static final String SELECT_ALL_REQUESTS_QUERY = "SELECT * FROM `requests`";

    private static final String DELETE_REQUEST_BY_ID_QUERY = "DELETE FROM `requests` WHERE `id_request` = ? ";

    @Override
    public void insert(Request request) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_REQUEST_QUERY);

            preparedStatement.setInt(1, request.getClientId());
            preparedStatement.setInt(2, request.getAdministratorId());
            preparedStatement.setInt(3, request.getSeatsNumber());
            preparedStatement.setDate(4, new java.sql.Date(request.getCheckinDate().getTime()));
            preparedStatement.setInt(5, request.getDaysStayNumber());
            preparedStatement.setBoolean(6, request.isFullPay());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot insert request", e);
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
    public void update(Request request) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_REQUEST_QUERY);

            preparedStatement.setInt(1, request.getClientId());
            preparedStatement.setInt(2, request.getAdministratorId());
            preparedStatement.setInt(3, request.getSeatsNumber());
            preparedStatement.setDate(4, new java.sql.Date(request.getCheckinDate().getTime()));
            preparedStatement.setInt(5, request.getDaysStayNumber());
            preparedStatement.setBoolean(6, request.isFullPay());
            preparedStatement.setInt(7, request.getPayment());
            preparedStatement.setBoolean(8, request.isAccepted());
            preparedStatement.setInt(9, request.getId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot update request", e);
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
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_REQUEST_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot delete request", e);
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
    public List<Request> findByClientId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Request> requests = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_REQUESTS_BY_CLIENT_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Request request = new Request();
                request.setId(resultSet.getInt(1));
                request.setClientId(resultSet.getInt(2));
                request.setAdministratorId(resultSet.getInt(3));
                request.setSeatsNumber(resultSet.getInt(4));
                request.setCheckinDate(resultSet.getDate(5));
                request.setDaysStayNumber(resultSet.getInt(6));
                request.setFullPay(resultSet.getBoolean(7));
                request.setPayment(resultSet.getInt(8));
                request.setAccepted(resultSet.getBoolean(9));
                requests.add(request);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select requests by client id", e);
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

        return requests;
    }

    @Override
    public List<Request> findByAdministratorId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Request> requests = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_REQUESTS_BY_ADMINISTRATOR_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Request request = new Request();
                request.setId(resultSet.getInt(1));
                request.setClientId(resultSet.getInt(2));
                request.setAdministratorId(resultSet.getInt(3));
                request.setSeatsNumber(resultSet.getInt(4));
                request.setCheckinDate(resultSet.getDate(5));
                request.setDaysStayNumber(resultSet.getInt(6));
                request.setFullPay(resultSet.getBoolean(7));
                request.setPayment(resultSet.getInt(8));
                request.setAccepted(resultSet.getBoolean(9));
                requests.add(request);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select requests by administrator id", e);
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

        return requests;
    }

    @Override
    public List<Request> selectAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Request> requests = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(SELECT_ALL_REQUESTS_QUERY);

            while(resultSet.next()){
                Request request = new Request();
                request.setId(resultSet.getInt(1));
                request.setClientId(resultSet.getInt(2));
                request.setAdministratorId(resultSet.getInt(3));
                request.setSeatsNumber(resultSet.getInt(4));
                request.setCheckinDate(resultSet.getDate(5));
                request.setDaysStayNumber(resultSet.getInt(6));
                request.setFullPay(resultSet.getBoolean(7));
                request.setPayment(resultSet.getInt(8));
                request.setAccepted(resultSet.getBoolean(9));
                requests.add(request);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select all requests", e);
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

        return requests;
    }

    public void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e){
            LOGGER.error("Can not close statement");
        }
    }


    public void closeResultSet(ResultSet resultSet){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e){
            LOGGER.error("Can not close result set");
        }
    }
}
