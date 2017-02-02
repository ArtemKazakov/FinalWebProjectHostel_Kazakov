package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link RentalRequest} entity.
 */
public interface RequestDAO {

    /**
     * Inserts a new rental request into a data source.
     *
     * @param rentalRequest a rental request object for insertion
     * @return identity key, which this entry got in data source
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    Integer insert(RentalRequest rentalRequest) throws DAOException;

    /**
     * Updates a rental request in a data source.
     *
     * @param rentalRequest a rental request object for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void update(RentalRequest rentalRequest) throws DAOException;

    /**
     * Deletes a rental request from a data source by id.
     *
     * @param id an id of deleting rental request
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void delete(int id) throws DAOException;

    /**
     * Gives a rental request from a data source by id.
     *
     * @param id an id of a desired rental request
     * @return a rental request object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    RentalRequest findById(int id) throws DAOException;

    /**
     * Gives a list of rental requests from a data source by client id.
     *
     * @param id an client id of desired rental requests
     * @return a {@link List} of rental requests containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<RentalRequest> findByClientId(int id) throws DAOException;

    /**
     * Gives a list of rental requests from a data source by administrator id.
     *
     * @param id an administrator id of desired rental requests
     * @return a {@link List} of rental requests containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<RentalRequest> findByAdministratorId(int id) throws DAOException;

    /**
     * Gives a list of all rental requests from a data source.
     *
     * @return a {@link List} of rental requests
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<RentalRequest> findAll() throws DAOException;

    /**
     * Gives a list of all rental requests from a data source.
     *
     * @param start a number from which entries will be returned
     * @param amount of entries
     * @return a {@link List} of rental requests
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<RentalRequest> findAllLimited(int start, int amount) throws DAOException;

    /**
     * Gives number of requests in a data source.
     *
     * @return count of requests
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    int selectRequestCount() throws DAOException;
}
