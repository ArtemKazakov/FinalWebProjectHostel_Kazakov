package com.epam.hostel.dao.connectionmanager;

import com.epam.hostel.dao.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Stores a limit number of connections.
 */
public class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private static final String DB_PROPERTY_RESOURCE_BUNDLE = "mysql-connection";
    private static final String DRIVER_CLASS_NAME_PROPERTY = "driverClassName";
    private static final String HOST_CONNECTION_STRING_PROPERTY = "hostConnectionString";
    private static final String DATABASE_NAME_PROPERTY = "databaseName";
    private static final String USER_LOGIN_PROPERTY = "userLogin";
    private static final String USER_PASSWORD_PROPERTY = "userPassword";
    private static final String NUMBER_OF_CONNECTIONS_PROPERTY = "numberOfConnections";


    private static final ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usedConnections;
    private static ReentrantLock lock = new ReentrantLock();
    private volatile boolean isInit = false;


    private ConnectionPool() {
    }

    /**
     * Creates a limit number of connections.
     * @throws ConnectionPoolException if can`t initialize pool.
     */
    public void init() throws ConnectionPoolException {
        try {
            lock.lock();
            if (!isInit) {
                ResourceBundle dbProperty = ResourceBundle.getBundle(DB_PROPERTY_RESOURCE_BUNDLE);
                int poolSize = Integer.parseInt(dbProperty.getString(NUMBER_OF_CONNECTIONS_PROPERTY));
                availableConnections = new ArrayBlockingQueue<>(poolSize);
                usedConnections = new ArrayBlockingQueue<>(poolSize);

                try {
                    Class.forName(dbProperty.getString(DRIVER_CLASS_NAME_PROPERTY));
                    for (int i = 0; i < poolSize; i++) {
                        Connection connection = DriverManager.getConnection(
                                dbProperty.getString(HOST_CONNECTION_STRING_PROPERTY) + dbProperty.getString(DATABASE_NAME_PROPERTY),
                                dbProperty.getString(USER_LOGIN_PROPERTY),
                                dbProperty.getString(USER_PASSWORD_PROPERTY)
                        );

                        connection.setAutoCommit(false);
                        availableConnections.add(connection);
                    }
                    isInit = true;

                    logger.info("Connection pool was successfully initialized");
                } catch (ClassNotFoundException | SQLException e) {
                    throw new ConnectionPoolException("Cannot init a pool", e);
                }
            } else {
                throw new ConnectionPoolException("Try to init already init pool");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Closes all of the connections.
     * @throws ConnectionPoolException if can`t destroy pool.
     */
    public void destroy() throws ConnectionPoolException {
        try {
            lock.lock();
            if (isInit) {
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
                    isInit = false;

                    logger.info("Connection pool was successfully destroyed");
                } catch (SQLException e) {
                    throw new ConnectionPoolException("Cannot destroy a pool", e);
                }
            } else {
                throw new ConnectionPoolException("Try to destroy not init pool");
            }
        } finally {
            lock.unlock();
        }
    }


    /**
     * Gives {@link ConnectionPool} instance.
     * @return ConnectionPool instance.
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Gives a free connection
     * @return connection
     * @throws ConnectionPoolException if error while getting connection.
     */
    public Connection getConnection() throws ConnectionPoolException {
        try {
            Connection connection = availableConnections.take();
            usedConnections.add(connection);
            return connection;
        } catch(InterruptedException e){
            throw new ConnectionPoolException("Taking interrupted connection", e);
        }
    }

    /**
     * Retrieves connection to a {@link ConnectionPool}
     * @param connection a connection object
     * @throws ConnectionPoolException if can`t retrieve connection of connection was created outside thr Connection Pool
     */
    public void freeConnection(Connection connection) throws ConnectionPoolException {
        if(usedConnections.contains(connection)){
            try {
                usedConnections.remove(connection);
                availableConnections.put(connection);
            } catch(InterruptedException e){
                throw new ConnectionPoolException("Can't free connection", e);
            }
        } else {
            throw new ConnectionPoolException("Try to close not a pool connection");
        }
    }

}
