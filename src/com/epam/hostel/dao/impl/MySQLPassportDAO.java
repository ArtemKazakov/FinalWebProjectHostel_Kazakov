package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by ASUS on 27.10.2016.
 */
public class MySQLPassportDAO implements PassportDAO {
    Logger LOGGER = Logger.getRootLogger();

    private static final String INSERT_USER_PASSPORT_QUERY = "INSERT INTO `passports` " +
            "(`identification_number`, `series`, `surname`, `name`, `patronymic`, `birthday`) " +
            "VALUES (?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE_USER_PASSPORT_QUERY = "UPDATE `passports` " +
            "SET `identification_number` = ?, `series` = ?, `surname` = ?, `name` = ?, `patronymic` = ?, `birthday` = ? " +
            "WHERE `id_passport` = ? ";

    private static final String SELECT_PASSPORT_BY_ID_QUERY = "SELECT * FROM `passports` WHERE `id_passport` = ? ";

    @Override
    public int insert(Passport passport) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int key = 0;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER_PASSPORT_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, passport.getIdentificationNumber());
            preparedStatement.setString(2, passport.getSeries());
            preparedStatement.setString(3, passport.getSurname());
            preparedStatement.setString(4, passport.getName());
            preparedStatement.setString(5, passport.getPatronymic());
            preparedStatement.setDate(6, new java.sql.Date(passport.getBirthday().getTime()));

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            key = resultSet.getInt(1);

            return key;
        } catch (InterruptedException | ConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot insert passport", e);
        } finally {
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    throw new DAOException("Cannot free a connection from Connection Pool", e);
                }
            }
        }
    }

    @Override
    public void update(Passport passport) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_PASSPORT_QUERY);

            preparedStatement.setInt(1, passport.getIdentificationNumber());
            preparedStatement.setString(2, passport.getSeries());
            preparedStatement.setString(3, passport.getSurname());
            preparedStatement.setString(4, passport.getName());
            preparedStatement.setString(5, passport.getPatronymic());
            preparedStatement.setDate(6, new java.sql.Date(passport.getBirthday().getTime()));
            preparedStatement.setInt(7, passport.getId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot update passport", e);
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
    public Passport findById(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Passport passport = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PASSPORT_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                passport = new Passport();
                passport.setId(resultSet.getInt(1));
                passport.setIdentificationNumber(resultSet.getInt(2));
                passport.setSeries(resultSet.getString(3));
                passport.setSurname(resultSet.getString(4));
                passport.setName(resultSet.getString(5));
                passport.setPatronymic(resultSet.getString(6));
                passport.setBirthday(resultSet.getDate(7));
            }
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot find passport by id", e);
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

        return passport;
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
