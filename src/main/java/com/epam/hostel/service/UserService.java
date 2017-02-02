package com.epam.hostel.service;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the {@link User} entity and relate with it.
 */
public interface UserService {

    /**
     * Updates a user account and passport in a data source
     *
     * @param user     an user account
     * @param passport an user passport
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void updateUser(User user, Passport passport) throws ServiceException;

    /**
     * Deletes a user from a data source by id
     *
     * @param id an id of user for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void deleteUser(int id) throws ServiceException;

    /**
     * Updates a user ban status in a data source
     *
     * @param id     an id of the user
     * @param banned a status of ban
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void banUser(int id, boolean banned) throws ServiceException;

    /**
     * Return a user from a data source by id and role
     *
     * @param id      an id of the user
     * @param isAdmin a role of the user
     * @return a user
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    User getUserByIdAndRole(int id, boolean isAdmin) throws ServiceException;

    /**
     * Return all users from a data source
     *
     * @param start  the number from which accounts will be returned
     * @param amount of users
     * @return a {@link List} of users
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<User> getAllUsers(int start, int amount) throws ServiceException;

    /**
     * Returns number of users in data source.
     *
     * @return amount of users
     * @throws ServiceException if error occurred with data source
     */
    int getUsersCount() throws ServiceException;
}
