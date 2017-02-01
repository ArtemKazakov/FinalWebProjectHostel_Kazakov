package com.epam.hostel.service.impl;

import com.epam.hostel.dao.PoolDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.PoolService;
import com.epam.hostel.service.exception.ServiceException;

/**
 * Provides work with a data source.
 */
public class PoolServiceImpl implements PoolService {

    /**
     * Initialize data source for work with it
     */
    @Override
    public void init() throws ServiceException {
        try {
            PoolDAO poolDAO = DAOFactory.getInstance().getPoolDAO();
            poolDAO.init();
        } catch (DAOException e) {
            throw new ServiceException("Cannot init a pool", e);
        }
    }

    /**
     * Destroy held resources for work with data source
     */
    @Override
    public void destroy() throws ServiceException {
        try {
            PoolDAO poolDAO = DAOFactory.getInstance().getPoolDAO();
            poolDAO.destroy();
        } catch (DAOException e) {
            throw new ServiceException("Cannot destroy a pool", e);
        }
    }
}
