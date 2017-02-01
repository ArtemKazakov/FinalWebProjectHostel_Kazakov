package com.epam.hostel.dao.transaction;

import com.epam.hostel.dao.exception.DAOException;

import java.sql.Connection;
import java.util.concurrent.Callable;

/**
 * Contains methods used for transaction management, that control work with a data source.
 */
public interface TransactionManager {

    /**
     * Garanted, that all code in this method will be
     * processed in single transaction and on the end
     * a result will be {@link Connection#commit()} in case
     * of success or {@link Connection#rollback()} otherwise
     *
     * @param unitOfWork A {@link Callable} object, that has the method
     *                   {@link Callable#call()}, which contents all actions,
     *                   that should be single transaction
     * @param <T>        Parameter for return value
     * @return object, getting from data source
     * @throws DAOException in case of some DAO layer exception
     */
    <T> T doInTransaction(Callable<T> unitOfWork) throws DAOException;

}
