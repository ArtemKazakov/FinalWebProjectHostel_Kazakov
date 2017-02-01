package com.epam.hostel.controller.listener;


import com.epam.hostel.controller.exception.ConnectionPoolListenerException;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.factory.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initialize and destroys the Connection Pool at the same time as ServletContext.
 */
public class ConnectionPoolListener implements ServletContextListener {

    public ConnectionPoolListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServiceFactory.getInstance().getPoolService().init();
        } catch (ServiceException e) {
            throw new ConnectionPoolListenerException("Cannot init a pool", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServiceFactory.getInstance().getPoolService().destroy();
        } catch (ServiceException e) {
            throw new ConnectionPoolListenerException("Cannot destroy pool", e);
        }
    }
}
