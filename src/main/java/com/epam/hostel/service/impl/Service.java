package com.epam.hostel.service.impl;

import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

/**
 * Provides some useful methods for work with services.
 */
public abstract class Service {
    private static final Logger logger = Logger.getLogger(Service.class);

    /**
     * Performs given operations inside of {@link TransactionManager#doInTransaction(Callable)}
     * method.
     * @param exceptionMessage message, that setting like an message in case of exception
     * @param callable implementation of {@link Callable} interface, which
     *        contains all operations, that should be a single transaction
     * @param <T> a type of result
     * @return result of transaction
     * @throws ServiceException in case of error occurred with database
     */
    public final <T> T service(String exceptionMessage, Callable<T> callable) throws ServiceException {
        try {
            return TransactionManagerImpl.getInstance().doInTransaction(callable);
        } catch(DAOException e){
            logger.error(e);
            throw new ServiceException(exceptionMessage, e);
        }
    }
}
