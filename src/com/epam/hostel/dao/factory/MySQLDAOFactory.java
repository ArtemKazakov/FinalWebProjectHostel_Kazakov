package com.epam.hostel.dao.factory;

import com.epam.hostel.dao.*;
import com.epam.hostel.dao.impl.*;

/**
 * Created by ASUS on 29.10.2016.
 */
public class MySQLDAOFactory extends DAOFactory {
    private static final MySQLDAOFactory INSTANCE = new MySQLDAOFactory();

    private final UserDAO userDAO = new MySQLUserDAO();
    private final PassportDAO passportDAO = new MySQLPassportDAO();
    private final PoolDAO poolDAO = new MySQLPoolDAO();
    private final RoomDAO roomDAO = new MySQLRoomDAO();
    private final DiscountDAO discountDAO = new MySQLDiscountDAO();
    private final RequestDAO requestDAO = new MySQLRequestDAO();
    private final ScheduleRecordDAO scheduleRecordDAO = new MySQLScheduleRecordDAO();

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

    @Override
    public DiscountDAO getDiscountDAO() {
        return discountDAO;
    }

    @Override
    public RequestDAO getRequestDAO() {
        return requestDAO;
    }

    @Override
    public ScheduleRecordDAO getScheduleRecordDAO() {
        return scheduleRecordDAO;
    }
}
