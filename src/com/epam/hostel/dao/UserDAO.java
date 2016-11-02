package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ASUS on 19.10.2016.
 */
public interface UserDAO {

    Logger LOGGER = Logger.getRootLogger();

    void insert(User user) throws DAOException;

    User findByLogin(String login) throws DAOException;

    default void closeStatement(Statement statement) throws DAOException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e){
            LOGGER.error("can`t close statement");
            throw new DAOException("Cannot free a connection from Connection Pool", e);
        }
    }


    default void closeResultSet(ResultSet resultSet) throws DAOException{
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e){
            LOGGER.error("can`t close result set");
            throw new DAOException("Cannot free a connection from Connection Pool", e);

        }
    }


}
