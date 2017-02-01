package com.epam.hostel.service;


import com.epam.hostel.bean.entity.User;
import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.service.exception.ServiceException;

/**
 * Provides a business-logic with the {@link User} entity and the {@link Passport} entity.
 */
public interface SiteService {

    /**
     * Checks if a user with this login and password can log in to the system.
     *
     * @param login    a login of the user
     * @param password a password of the user
     * @return an user object if log in success
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    User logIn(String login, byte[] password) throws ServiceException;

    /**
     * Registers a new user to the system.
     *
     * @param user     an user account
     * @param passport an user passport
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void registration(User user, Passport passport) throws ServiceException;
}

