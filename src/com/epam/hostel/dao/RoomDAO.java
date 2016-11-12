package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public interface RoomDAO {
    Logger LOGGER = Logger.getRootLogger();

    List<Room> getAllRooms() throws DAOException;

    // дефолтные методы - это конечно хорошо, только здесь они совсем не к месту
    // RoomDAO задает поведения безотносительно источника данных
    // и вдруг получает метод от источника зависящие
    // переноси в другой интерфейс и клади его в impl
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
