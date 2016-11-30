package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.DiscountDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 15.11.2016.
 */
public class MySQLDiscountDAO implements DiscountDAO {

    Logger LOGGER = Logger.getRootLogger();

    private static final String INSERT_DISCOUNT_QUERY = "INSERT INTO `discounts` " +
            "(`id_client`, `discount_value`, `id_administrator`) " +
            "VALUES (?, ?, ?) ";

    private static final String UPDATE_DISCOUNT_QUERY = "UPDATE `discounts` " +
            "SET `id_client` = ?, `discount_value` = ?, `id_administrator` = ?, `discount_used` = ? " +
            "WHERE `id_discount` = ? ";

    private static final String SELECT_DISCOUNTS_BY_CLIENT_ID_QUERY = "SELECT * FROM `discounts` WHERE `id_client` = ? ";

    private static final String SELECT_DISCOUNTS_BY_ADMINISTRATOR_ID_QUERY = "SELECT * FROM `discounts` " +
            "WHERE `id_administrator` = ? ";

    private static final String SELECT_ALL_DISCOUNTS_QUERY = "SELECT * FROM `discounts`";

    private static final String DELETE_DISCOUNT_BY_ID_QUERY = "DELETE FROM `discounts` WHERE `id_discount` = ? ";

    @Override
    public void insert(Discount discount) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_DISCOUNT_QUERY);

            preparedStatement.setInt(1, discount.getClientId());
            preparedStatement.setInt(2, discount.getValue());
            preparedStatement.setInt(3, discount.getAdministratorId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot insert discount", e);
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
    public void update(Discount discount) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_DISCOUNT_QUERY);

            preparedStatement.setInt(1, discount.getClientId());
            preparedStatement.setInt(2, discount.getValue());
            preparedStatement.setInt(3, discount.getAdministratorId());
            preparedStatement.setBoolean(4, discount.isUsed());
            preparedStatement.setInt(5, discount.getId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
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
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_DISCOUNT_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot delete discount", e);
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
    public List<Discount> findByClientId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Discount> discounts = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_DISCOUNTS_BY_CLIENT_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Discount discount = new Discount();
                discount.setId(resultSet.getInt(1));
                discount.setClientId(resultSet.getInt(2));
                discount.setValue(resultSet.getInt(3));
                discount.setAdministratorId(resultSet.getInt(4));
                discount.setUsed(resultSet.getBoolean(5));
                discounts.add(discount);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select discounts by client id", e);
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

        return discounts;
    }

    @Override
    public List<Discount> findByAdministratorId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Discount> discounts = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_DISCOUNTS_BY_ADMINISTRATOR_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Discount discount = new Discount();
                discount.setId(resultSet.getInt(1));
                discount.setClientId(resultSet.getInt(2));
                discount.setValue(resultSet.getInt(3));
                discount.setAdministratorId(resultSet.getInt(4));
                discount.setUsed(resultSet.getBoolean(5));
                discounts.add(discount);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select discounts by administrator id", e);
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

        return discounts;
    }

    @Override
    public List<Discount> findAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Discount> discounts = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(SELECT_ALL_DISCOUNTS_QUERY);

            while(resultSet.next()){
                Discount discount = new Discount();
                discount.setId(resultSet.getInt(1));
                discount.setClientId(resultSet.getInt(2));
                discount.setValue(resultSet.getInt(3));
                discount.setAdministratorId(resultSet.getInt(4));
                discount.setUsed(resultSet.getBoolean(5));
                discounts.add(discount);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select all discounts", e);
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

        return discounts;
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
