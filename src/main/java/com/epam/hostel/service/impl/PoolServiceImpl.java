package com.epam.hostel.service.impl;

import com.epam.hostel.dao.PoolDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.PoolService;
import com.epam.hostel.service.exception.ServiceException;

public class PoolServiceImpl implements PoolService {

    @Override
    public void init() throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
            PoolDAO poolDAO = daoFactory.getPoolDAO();
            poolDAO.init();
        } catch (DAOException e) {
            throw new ServiceException("Cannot init a pool", e);
        }
    }

    @Override
    public void destroy() throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
            PoolDAO poolDAO = daoFactory.getPoolDAO();
            poolDAO.destroy();
        } catch (DAOException e) {
            throw new ServiceException("Cannot destroy a pool", e);
        }
    }
}
