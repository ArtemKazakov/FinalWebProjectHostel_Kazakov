package com.epam.hostel.dao.factory;

import com.epam.hostel.dao.*;
import com.epam.hostel.util.injection.Injectable;
import com.epam.hostel.util.injection.annotation.InjectBean;

/**
 * Provides a logic of instancing DAO objects.
 */
public class DAOFactory implements Injectable{
    private static final DAOFactory INSTANCE = new DAOFactory();

    @InjectBean(beanName = "userDAO")
    private UserDAO userDAO;

    @InjectBean(beanName = "passportDAO")
    private PassportDAO passportDAO;

    @InjectBean(beanName = "poolDAO")
    private PoolDAO poolDAO;

    @InjectBean(beanName = "roomDAO")
    private RoomDAO roomDAO;

    @InjectBean(beanName = "discountDAO")
    private DiscountDAO discountDAO;

    @InjectBean(beanName = "requestDAO")
    private RequestDAO requestDAO;

    @InjectBean(beanName = "scheduleRecordDAO")
    private ScheduleRecordDAO scheduleRecordDAO;

    private DAOFactory(){}

    /**
     * Returns the instance of the DAOFactory.

     * @return the instance of the DAOFactory
     */
    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Gives {@link UserDAO} implementation.
     *
     * @return UserDAO implementation
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Gives {@link PassportDAO} implementation.
     *
     * @return PassportDAO implementation
     */
    public PassportDAO getPassportDAO() {
        return passportDAO;
    }

    /**
     * Gives {@link PoolDAO} implementation.
     *
     * @return PoolDAO implementation
     */
    public PoolDAO getPoolDAO() {
        return poolDAO;
    }

    /**
     * Gives {@link RoomDAO} implementation.
     *
     * @return RoomDAO implementation
     */
    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    /**
     * Gives {@link DiscountDAO} implementation.
     *
     * @return DiscountDAO implementation
     */
    public DiscountDAO getDiscountDAO() {
        return discountDAO;
    }

    /**
     * Gives {@link RequestDAO} implementation.
     *
     * @return RequestDAO implementation
     */
    public RequestDAO getRequestDAO() {
        return requestDAO;
    }

    /**
     * Gives {@link ScheduleRecordDAO} implementation.
     *
     * @return ScheduleRecordDAO implementation
     */
    public ScheduleRecordDAO getScheduleRecordDAO() {
        return scheduleRecordDAO;
    }

}
