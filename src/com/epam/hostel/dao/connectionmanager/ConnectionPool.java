package com.epam.hostel.dao.connectionmanager;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 27.10.2016.
 */
public class ConnectionPool {

    private static final Logger LOGGER = Logger.getRootLogger();

    private static final String RESOURCE_BUNDLE_NAME = "mysql-connection";
    private static final String DRIVER_CLASS_NAME_PROPERTY = "driverClassName";
    private static final String HOST_CONNECTION_STRING_PROPERTY = "hostConnectionString";
    private static final String DATABASE_NAME_PROPERTY = "databaseName";
    private static final String USER_LOGIN_PROPERTY = "userLogin";
    private static final String USER_PASSWORD_PROPERTY = "userPassword";
    private static final String NUMBER_OF_CONNECTIONS_PROPERTY  = "numberOfConnections";

    private String hostConnectionString;
    private String databaseName;
    private String userLogin;
    private String userPassword;

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
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
            String driverClassName = resourceBundle.getString(DRIVER_CLASS_NAME_PROPERTY);
            String hostConnectionString = resourceBundle.getString(HOST_CONNECTION_STRING_PROPERTY);
            String databaseName = resourceBundle.getString(DATABASE_NAME_PROPERTY);
            String userLogin = resourceBundle.getString(USER_LOGIN_PROPERTY);
            String userPassword = resourceBundle.getString(USER_PASSWORD_PROPERTY);
            String numberOfConnectionsString = resourceBundle.getString(NUMBER_OF_CONNECTIONS_PROPERTY);

            this.hostConnectionString = hostConnectionString;
            this.databaseName = databaseName;
            this.userLogin = userLogin;
            this.userPassword = userPassword;

            try {
                Class.forName(driverClassName);
                for (int i = 0; i < Integer.parseInt(numberOfConnectionsString); i++) {
                    Connection newConnection = DriverManager.getConnection(hostConnectionString + databaseName,
                            userLogin, userPassword);

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
            LOGGER.info("Connection pool was created");
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
                    connection = DriverManager.getConnection(hostConnectionString + databaseName,
                            userLogin, userPassword);

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
