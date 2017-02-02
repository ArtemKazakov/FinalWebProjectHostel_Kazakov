package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link User} entity.
 */
public interface UserDAO {

    /**
     * Inserts a new user into a data source.
     *
     * @param user a user object for insertion
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void insert(User user) throws DAOException;

    /**
     * Updates a user in a data source.
     *
     * @param user a user object for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void update(User user) throws DAOException;

    /**
     * Updates a user ban status in a data source.
     *
     * @param user a user object that contains ban status for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void updateBanned(User user) throws DAOException;

    /**
     * Deletes a user from a data source by id.
     *
     * @param id an id of deleting user
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void delete(int id) throws DAOException;

    /**
     * Gives a user from a data source by login.
     *
     * @param login a login of a desired user
     * @return a user object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    User findByLogin(String login) throws DAOException;

    /**
     * Gives a user from a data source by id and role.
     *
     * @param id an id of a desired user
     * @param isAdmin a status of a desired user
     * @return a user object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    User findByIdAndRole(int id, boolean isAdmin) throws DAOException;

    /**
     * Gives a list of all users from a data source.
     *
     * @param start  a number from which entries will be returned
     * @param amount of entries
     * @return a {@link List} of users
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<User> findAll(int start, int amount) throws DAOException;

    /**
     * Gives number of users in a data source.
     *
     * @return count of users
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    int selectUserCount() throws DAOException;

}
