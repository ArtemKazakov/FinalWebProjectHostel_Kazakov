package com.epam.hostel.dao.factory;

import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.PoolDAO;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.impl.MySQLPassportDAO;
import com.epam.hostel.dao.impl.MySQLPoolDAO;
import com.epam.hostel.dao.impl.MySQLRoomDAO;
import com.epam.hostel.dao.impl.MySQLUserDAO;

/**
 * Created by ASUS on 29.10.2016.
 */
public class MySQLDAOFactory extends DAOFactory {
    private static final MySQLDAOFactory INSTANCE = new MySQLDAOFactory();

    private final UserDAO userDAO = new MySQLUserDAO();
    private final PassportDAO passportDAO = new MySQLPassportDAO();
    private final PoolDAO poolDAO = new MySQLPoolDAO();
    private final RoomDAO roomDAO = new MySQLRoomDAO();

    private MySQLDAOFactory(){}

    public static MySQLDAOFactory getInstance(){
        return INSTANCE;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public PassportDAO getPassportDAO() {
        return passportDAO;
    }

    @Override
    public PoolDAO getPoolDAO() {
        return poolDAO;
    }

    @Override
    public RoomDAO getRoomDAO() {
        return roomDAO;
    }
}
