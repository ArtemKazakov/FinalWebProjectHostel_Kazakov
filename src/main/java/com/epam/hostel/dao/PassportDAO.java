package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.dao.exception.DAOException;

/**
 * Provides a DAO-logic for the {@link Passport} entity.
 */
public interface PassportDAO {

    /**
     * Inserts a new passport into a data source.
     *
     * @param passport a passport object for insertion
     * @return identity key, which this entry got in data source
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    Integer insert(Passport passport) throws DAOException;

    /**
     * Updates a passport in a data source.
     *
     * @param passport a passport object for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void update(Passport passport) throws DAOException;

    /**
     * Deletes a passport from a data source by id.
     *
     * @param id an id of deleting passport
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void delete(int id) throws DAOException;

    /**
     * Gives a passport from a data source by id.
     *
     * @param id an id of a desired passport
     * @return a passport object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    Passport findById(int id) throws DAOException;

}
