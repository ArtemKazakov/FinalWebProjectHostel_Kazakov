package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link Discount} entity.
 */
public interface DiscountDAO {

    /**
     * Inserts a new discount into a data source.
     *
     * @param discount a discount object for insertion
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void insert(Discount discount) throws DAOException;

    /**
     * Updates a discount in a data source.
     *
     * @param discount a discount object for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void update(Discount discount) throws DAOException;

    /**
     * Updates a discount value in a data source.
     *
     * @param discount a discount object that contains value for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void updateValue(Discount discount) throws DAOException;

    /**
     * Deletes a discount from a data source by id.
     *
     * @param id an id of deleting discount
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void delete(int id) throws DAOException;

    /**
     * Gives a discount from a data source by id.
     *
     * @param id an id of a desired discount
     * @return a discount object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    Discount findById(int id) throws DAOException;

    /**
     * Gives a list of discounts from a data source by client id.
     *
     * @param id an client id of desired discounts
     * @return a {@link List} of discounts containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<Discount> findByClientId(int id) throws DAOException;

    /**
     * Gives a list of unused discounts from a data source by client id.
     *
     * @param id an client id of desired discounts
     * @return a {@link List} of discounts containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<Discount> findUnusedByClientId(int id) throws DAOException;

    /**
     * Gives a list of discounts from a data source by administrator id.
     *
     * @param id an administrator id of desired discounts
     * @return a {@link List} of discounts containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<Discount> findByAdministratorId(int id) throws DAOException;

    /**
     * Gives a list of all discounts from a data source.
     *
     * @return a {@link List} of discounts
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<Discount> findAll() throws DAOException;

}
