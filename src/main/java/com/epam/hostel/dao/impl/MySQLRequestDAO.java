package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.User;
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
    private final static Logger LOGGER = Logger.getRootLogger();

    private static final String INSERT_REQUEST_QUERY = "INSERT INTO `requests` " +
            "(`id_client`, `seats_number`, `checkin_date`, `days_stay`, `request_type`, `payment`) " +
            "VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE_REQUEST_QUERY = "UPDATE `requests` " +
            "SET `id_client` = ?, `id_administrator` = ?, `seats_number` = ?, `checkin_date` = ?, " +
            "`days_stay` = ?, `request_type` = ?, `payment` = ?, `request_status` = ? " +
            "WHERE `id_request` = ? ";

    private static final String SELECT_REQUEST_BY_ID_QUERY = "SELECT * FROM `requests` WHERE `id_request` = ? ";

    private static final String SELECT_REQUESTS_BY_CLIENT_ID_QUERY = "SELECT * FROM `requests` WHERE `id_client` = ? ";

    private static final String SELECT_REQUESTS_BY_ADMINISTRATOR_ID_QUERY = "SELECT * FROM `requests` " +
            "WHERE `id_administrator` = ? ";

    private static final String SELECT_ALL_REQUESTS_QUERY = "SELECT * FROM `requests`";

    private static final String DELETE_REQUEST_BY_ID_QUERY = "DELETE FROM `requests` WHERE `id_request` = ? ";

    @Override
    public int insert(RentalRequest rentalRequest) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int key = 0;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_REQUEST_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, rentalRequest.getClient().getId());
            preparedStatement.setInt(2, rentalRequest.getSeatsNumber());
            preparedStatement.setDate(3, new Date(rentalRequest.getCheckInDate().getTime()));
            preparedStatement.setInt(4, rentalRequest.getDaysStayNumber());
            preparedStatement.setBoolean(5, rentalRequest.isFullPayment());
            preparedStatement.setInt(6, rentalRequest.getPayment());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            key = resultSet.getInt(1);
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot insert rentalRequest", e);
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

        return key;
    }

    @Override
    public void update(RentalRequest rentalRequest) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_REQUEST_QUERY);

            preparedStatement.setInt(1, rentalRequest.getClient().getId());
            preparedStatement.setInt(2, rentalRequest.getAdministrator().getId());
            preparedStatement.setInt(3, rentalRequest.getSeatsNumber());
            preparedStatement.setDate(4, new Date(rentalRequest.getCheckInDate().getTime()));
            preparedStatement.setInt(5, rentalRequest.getDaysStayNumber());
            preparedStatement.setBoolean(6, rentalRequest.isFullPayment());
            preparedStatement.setInt(7, rentalRequest.getPayment());
            preparedStatement.setBoolean(8, rentalRequest.getAccepted());
            preparedStatement.setInt(9, rentalRequest.getId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot update rentalRequest", e);
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
    public RentalRequest findById(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RentalRequest rentalRequest = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_REQUEST_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentalRequest = new RentalRequest();
                rentalRequest.setId(resultSet.getInt(1));
                User client = new User();
                client.setId(resultSet.getInt(2));
                rentalRequest.setClient(client);
                User administrator = new User();
                administrator.setId(resultSet.getInt(3));
                rentalRequest.setAdministrator(administrator);
                rentalRequest.setSeatsNumber(resultSet.getInt(4));
                rentalRequest.setCheckInDate(resultSet.getDate(5));
                rentalRequest.setDaysStayNumber(resultSet.getInt(6));
                rentalRequest.setFullPayment(resultSet.getBoolean(7));
                rentalRequest.setPayment(resultSet.getInt(8));
                rentalRequest.setAccepted((Boolean)resultSet.getObject(9));
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot select rental request by id", e);
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

        return rentalRequest;
    }

    @Override
    public List<RentalRequest> findByClientId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RentalRequest> rentalRequests = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_REQUESTS_BY_CLIENT_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                RentalRequest rentalRequest = new RentalRequest();
                rentalRequest.setId(resultSet.getInt(1));
                User client = new User();
                client.setId(resultSet.getInt(2));
                rentalRequest.setClient(client);
                User administrator = new User();
                administrator.setId(resultSet.getInt(3));
                rentalRequest.setAdministrator(administrator);
                rentalRequest.setSeatsNumber(resultSet.getInt(4));
                rentalRequest.setCheckInDate(resultSet.getDate(5));
                rentalRequest.setDaysStayNumber(resultSet.getInt(6));
                rentalRequest.setFullPayment(resultSet.getBoolean(7));
                rentalRequest.setPayment(resultSet.getInt(8));
                rentalRequest.setAccepted((Boolean)resultSet.getObject(9));
                rentalRequests.add(rentalRequest);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select rentalRequests by client id", e);
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

        return rentalRequests;
    }

    @Override
    public List<RentalRequest> findByAdministratorId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RentalRequest> rentalRequests = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_REQUESTS_BY_ADMINISTRATOR_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                RentalRequest rentalRequest = new RentalRequest();
                rentalRequest.setId(resultSet.getInt(1));
                User client = new User();
                client.setId(resultSet.getInt(2));
                rentalRequest.setClient(client);
                User administrator = new User();
                administrator.setId(resultSet.getInt(3));
                rentalRequest.setAdministrator(administrator);
                rentalRequest.setSeatsNumber(resultSet.getInt(4));
                rentalRequest.setCheckInDate(resultSet.getDate(5));
                rentalRequest.setDaysStayNumber(resultSet.getInt(6));
                rentalRequest.setFullPayment(resultSet.getBoolean(7));
                rentalRequest.setPayment(resultSet.getInt(8));
                rentalRequest.setAccepted((Boolean)resultSet.getObject(9));
                rentalRequests.add(rentalRequest);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select rentalRequests by administrator id", e);
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

        return rentalRequests;
    }

    @Override
    public List<RentalRequest> findAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<RentalRequest> rentalRequests = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(SELECT_ALL_REQUESTS_QUERY);

            while(resultSet.next()){
                RentalRequest rentalRequest = new RentalRequest();
                rentalRequest.setId(resultSet.getInt(1));
                User client = new User();
                client.setId(resultSet.getInt(2));
                rentalRequest.setClient(client);
                User administrator = new User();
                administrator.setId(resultSet.getInt(3));
                rentalRequest.setAdministrator(administrator);
                rentalRequest.setSeatsNumber(resultSet.getInt(4));
                rentalRequest.setCheckInDate(resultSet.getDate(5));
                rentalRequest.setDaysStayNumber(resultSet.getInt(6));
                rentalRequest.setFullPayment(resultSet.getBoolean(7));
                rentalRequest.setPayment(resultSet.getInt(8));
                rentalRequest.setAccepted((Boolean)resultSet.getObject(9));
                rentalRequests.add(rentalRequest);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select all rentalRequests", e);
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

        return rentalRequests;
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
