package com.epam.hostel.controller.listener;


import com.epam.hostel.service.PoolService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolListener implements ServletContextListener {

    public ConnectionPoolListener() {
    }


    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PoolService poolService = serviceFactory.getPoolService();
            poolService.init();
        } catch (ServiceException e) {
            throw new ConnectionPoolListenerException("Cannot init a pool", e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PoolService poolService = serviceFactory.getPoolService();
            poolService.destroy();
        } catch (ServiceException e) {
            throw new ConnectionPoolListenerException("Cannot destroy pool", e);
        }
    }
}
