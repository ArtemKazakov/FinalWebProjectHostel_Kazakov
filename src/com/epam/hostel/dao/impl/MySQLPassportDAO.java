package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;

import java.sql.*;

/**
 * Created by ASUS on 27.10.2016.
 */
public class MySQLPassportDAO implements PassportDAO {

    private static final String INSERT_USER_PASSPORT_QUERY = "INSERT INTO `passports` " +
            "(`identification_number`, `series`, `surname`, `name`, `lastname`, `birthday`) " +
            "VALUES (?, ?, ?, ?, ?, ?) ";

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
            preparedStatement.setString(5, passport.getLastName());
            preparedStatement.setDate(6, new java.sql.Date(passport.getBirthday().getTime()));

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            key = resultSet.getInt(1);

            return key;
        } catch (InterruptedException | ConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e){
            throw new DAOException("Error in DAO layer when adding passport", e);
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



}
