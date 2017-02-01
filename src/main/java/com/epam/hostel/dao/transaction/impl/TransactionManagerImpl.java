package com.epam.hostel.dao.transaction.impl;

import com.epam.hostel.dao.connectionmanager.BaseDataSource;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.exception.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.TransactionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import javax.sql.DataSource;

/**
 * Implementation of {@link TransactionManager}.
 * Extend {@link BaseDataSource} and it can provide a connection
 * for DAO layer. Should be setting liek a @{@link DataSource} for all
 * DAO implementations.
 */
public class TransactionManagerImpl extends BaseDataSource implements TransactionManager {
    private final static Logger logger = Logger.getLogger(TransactionManagerImpl.class);
    private static final TransactionManager instance = new TransactionManagerImpl();
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private TransactionManagerImpl() {
    }

    /**
     * Give {@link TransactionManager} implementation
     *
     * @return TransactionManager implementation
     */
    public static TransactionManager getInstance() {
        return instance;
    }

    /**
     * Garanted, that all code in this method will be
     * processed in single transaction and on the end
     * a result will be {@link Connection#commit()} in case
     * of success or {@link Connection#rollback()} otherwise
     *
     * @param unitOfWork a {@link Callable} object, that has the method
     *                   {@link Callable#call()}, which contents all actions,
     *                   that should be single transaction
     * @param <T>        parameter for return value
     * @return result of {@link Callable#call()} method
     * @throws DAOException in case of some DAO layer exception
     */
    @Override
    public <T> T doInTransaction(Callable<T> unitOfWork) throws DAOException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connectionHolder.set(connection);

            T result = unitOfWork.call();

            connection.commit();
            return result;
        } catch (Exception exception) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException sqlException) {
                    logger.error(sqlException);
                }
            }
            throw new DAOException(exception);
        } finally {
            if (connection != null) {
                try {
                    ConnectionPool.getInstance().freeConnection(connection);
                } catch (ConnectionPoolException poolException) {
                    logger.error(poolException);
                }
                connectionHolder.remove();
            }
        }
    }

    /**
     * Attempts to establish a connection with the data source that
     * this {@code DataSource} object represents.
     *
     * @return a connection to the data source
     */
    @Override
    public Connection getConnection() {
        return connectionHolder.get();
    }
}
