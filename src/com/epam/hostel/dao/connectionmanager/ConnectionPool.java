package com.epam.hostel.dao.connectionmanager;

import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 27.10.2016.
 */
public class ConnectionPool {

    private static final Logger LOGGER = Logger.getRootLogger();

    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String HOST_CONNECTION = "jdbc:mysql://localhost/hostel";
    private static final String USER_LOGIN = "root";
    private static final String USER_PASSWORD = "1234";
    private static final int NUMBER_OF_CONNECTIONS = 5;

    private volatile boolean isAvailable = false;
    private volatile boolean isInit = false;

    private static ConnectionPool instance;

    private List<Connection> availableConnections = new LinkedList<>();
    private List<Connection> usedConnections = new LinkedList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition atLeastOneFreeConnection = lock.newCondition();

    private ConnectionPool(){
    }

    public void init() throws ConnectionPoolException {
        if(!isInit) {
            try {
                Class.forName(DRIVER_CLASS_NAME);
                for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
                    Connection newConnection = DriverManager.getConnection(HOST_CONNECTION, USER_LOGIN, USER_PASSWORD);

                    lock.lock();
                    availableConnections.add(newConnection);
                    isAvailable = true;
                    lock.unlock();
                }
                isInit = true;
            } catch (ClassNotFoundException | SQLException e) {
                throw new ConnectionPoolException("Cannot init a pool", e);
            }
        }
        else {
            throw new ConnectionPoolException("Try to init already init pool");
        }
    }

    public void destroy() throws ConnectionPoolException {
        if(isInit) {
            lock.lock();
            try {
                for (Connection connection : availableConnections) {
                    connection.close();
                }
                availableConnections.clear();
                for (Connection connection : usedConnections) {
                    connection.close();
                }
                usedConnections.clear();
                isAvailable = false;
                isInit = false;
            } catch (SQLException e) {
                throw new ConnectionPoolException("Cannot destroy a pool", e);
            } finally {
                lock.unlock();
            }
        }
        else {
            throw new ConnectionPoolException("Try to destroy not init pool");
        }
    }


    public static ConnectionPool getInstance(){
        if (instance == null){
            instance = new ConnectionPool();
            LOGGER.info("Connection was created");
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException, ConnectionPoolException {
        if(isAvailable){
            lock.lock();
            try {
                while (availableConnections.isEmpty()){
                    atLeastOneFreeConnection.await();
                }

                Connection connection = availableConnections.remove(0);
                usedConnections.add(connection);
                return connection;
            } finally {
                lock.unlock();
            }
        }
        else {
            throw new ConnectionPoolException("Try to use pool when it is not available");
        }
    }

    public void freeConnection(Connection connection) throws SQLException, ConnectionPoolException {
        if(isAvailable){
            lock.lock();
            try {
                if (usedConnections.isEmpty() || !usedConnections.contains(connection)) {
                    throw new ConnectionPoolException("Try to free pool which was created not in Connection Pool");
                }
                usedConnections.remove(connection);

                if (connection.isClosed()) {
                    connection = DriverManager.getConnection(HOST_CONNECTION, USER_LOGIN, USER_PASSWORD);

                } else {
                    if (!connection.getAutoCommit()) {
                        connection.setAutoCommit(true);
                    }
                    if (connection.isReadOnly()) {
                        connection.setReadOnly(false);
                    }
                }

                availableConnections.add(connection);

                atLeastOneFreeConnection.signal();
            } finally {
                lock.unlock();
            }
        }
        else {
            throw new ConnectionPoolException("Try to use pool when it is not available");
        }
    }

}
