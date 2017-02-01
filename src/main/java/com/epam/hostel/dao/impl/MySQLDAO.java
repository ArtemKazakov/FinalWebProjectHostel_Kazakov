package com.epam.hostel.dao.impl;

import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.exception.NonTransactionInvocationException;
import com.epam.hostel.dao.impl.daofunction.DAOConsumer;
import com.epam.hostel.dao.impl.daofunction.DAOFunction;
import com.epam.hostel.dao.transaction.TransactionManager;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Provides methods for work with database like a data source,
 * that cover base operations with database.
 */
public abstract class MySQLDAO {
    private static final Logger logger = Logger.getLogger(MySQLDAO.class);

    /**
     * Performs an operation, that use {@link Statement#executeUpdate(String)} method
     * to do insert, delete or update operation with database.
     *
     * @param dataSource       a {@link DataSource} object, that provide a
     *                         connection with database
     * @param query            an SQL query
     * @param exceptionMessage message, that setting like an message in case of exception
     * @param dataSetter       a {@link DAOConsumer} that accepts
     *                         {@link PreparedStatement} and set on it all data for
     *                         given {@code query}
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    public final void doDataManipulation(DataSource dataSource, String query, String exceptionMessage,
                                         DAOConsumer<PreparedStatement> dataSetter) throws DAOException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = dataSource.getConnection();
            this.checkConnection(connection);
            preparedStatement = connection.prepareStatement(query);

            dataSetter.accept(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(exceptionMessage, e);
        } finally {
            close(preparedStatement);
        }
    }

    /**
     * Performs an operation, that use {@link Statement#executeUpdate(String)} method
     * to do insert operation with database and return generated key in database.
     *
     * @param dataSource       a {@link DataSource} object, that provide a
     *                         connection with database
     * @param query            an SQL query
     * @param exceptionMessage message, that setting like an message in case of exception
     * @param dataSetter       a {@link DAOConsumer} that accepts
     *                         {@link PreparedStatement} and set on it all data for
     *                         given {@code query}
     * @param creator          a {@link DAOFunction} that accepts
     *                         {@link ResultSet}, that will be contains the generated key,
     *                         and extracts it from ResultSet
     * @param <T>              a type of primary key in a database
     * @return a generated key
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    public final <T> T insert(DataSource dataSource, String query, String exceptionMessage,
                              DAOConsumer<PreparedStatement> dataSetter,
                              DAOFunction<ResultSet, T> creator) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = dataSource.getConnection();
            this.checkConnection(connection);
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            dataSetter.accept(preparedStatement);

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            return creator.apply(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(exceptionMessage, e);
        } finally {
            this.close(resultSet, preparedStatement);
        }
    }

    /**
     * Performs an select operation in database and used {@link PreparedStatement}.
     *
     * @param dataSource       a {@link DataSource} object, that provide a
     *                         connection with database
     * @param query            an SQL query
     * @param exceptionMessage message, that setting like an message in case of exception
     * @param dataSetter       a {@link DAOConsumer} that accepts
     *                         {@link PreparedStatement} and set on it all data for
     *                         given {@code query}
     * @param creator          a {@link DAOFunction} that accepts
     *                         {@link ResultSet}, that will be contains the result of query,
     *                         and parse this result in needed object
     * @param <T>              a type of expected objects
     * @return a parametrized {@link List} of objects from database
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    public final <T> List<T> select(DataSource dataSource, String query, String exceptionMessage,
                                    DAOConsumer<PreparedStatement> dataSetter,
                                    DAOFunction<ResultSet, T> creator) throws DAOException {
        return this.singleSelect(
                dataSource,
                query,
                exceptionMessage,
                dataSetter,
                resultSet -> {
                    List<T> list = new ArrayList<>();
                    while (resultSet.next()) {
                        list.add(creator.apply(resultSet));
                    }
                    return list;
                }
        );
    }

    /**
     * Performs an select operation in database and used {@link PreparedStatement}
     * and expected, that only one object will be returned.
     *
     * @param dataSource       a {@link DataSource} object, that provide a
     *                         connection with database
     * @param query            an SQL query
     * @param exceptionMessage message, that setting like an message in case of exception
     * @param dataSetter       a {@link DAOConsumer} that accepts
     *                         {@link PreparedStatement} and set on it all data for
     *                         given {@code query}
     * @param creator          a {@link DAOFunction} that accepts
     *                         {@link ResultSet}, that will be contains the result of query,
     *                         and parse this result in needed object
     * @param <T>              a type of expected object
     * @return an object from database
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    public final <T> T singleSelect(DataSource dataSource, String query, String exceptionMessage,
                                    DAOConsumer<PreparedStatement> dataSetter,
                                    DAOFunction<ResultSet, T> creator) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = dataSource.getConnection();
            this.checkConnection(connection);
            preparedStatement = connection.prepareStatement(query);

            dataSetter.accept(preparedStatement);

            resultSet = preparedStatement.executeQuery();

            return creator.apply(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(exceptionMessage, e);
        } finally {
            this.close(resultSet, preparedStatement);
        }
    }

    /**
     * Performs an select operation in database and used {@link Statement}.
     *
     * @param dataSource       a {@link DataSource} object, that provide a
     *                         connection with database
     * @param query            an SQL query
     * @param exceptionMessage message, that setting like an message in case of exception
     * @param creator          a {@link DAOFunction} that accepts
     *                         {@link ResultSet}, that will be contains the result of query,
     *                         and parse this result in needed object
     * @param <T>              a type of expected objects
     * @return a parametrized {@link List} of objects from database
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    public final <T> List<T> select(DataSource dataSource, String query, String exceptionMessage,
                                    DAOFunction<ResultSet, T> creator) throws DAOException {
        return this.singleSelect(
                dataSource,
                query,
                exceptionMessage,
                resultSet -> {
                    List<T> list = new ArrayList<>();
                    while (resultSet.next()) {
                        list.add(creator.apply(resultSet));
                    }
                    return list;
                }
        );
    }

    /**
     * Performs an select operation in database and used {@link Statement}
     * and expected, that only one object will be returned.
     *
     * @param dataSource       a {@link DataSource} object, that provide a
     *                         connection with database
     * @param query            an SQL query
     * @param exceptionMessage message, that setting like an message in case of exception
     * @param creator          a {@link DAOFunction} that accepts
     *                         {@link ResultSet}, that will be contains the result of query,
     *                         and parse this result in needed object
     * @param <T>              a type of expected object
     * @return an object from database
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    public final <T> T singleSelect(DataSource dataSource, String query, String exceptionMessage,
                                    DAOFunction<ResultSet, T> creator) throws DAOException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = dataSource.getConnection();
            this.checkConnection(connection);
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            return creator.apply(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(exceptionMessage, e);
        } finally {
            this.close(resultSet, statement);
        }
    }

    /**
     * Closes resources.
     * @param resources resources for closing
     */
    public void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }

    /**
     * Checks connection for that it invoke inside of
     * {@link TransactionManager#doInTransaction(Callable)}.
     * @param connection a connection for checking
     */
    public void checkConnection(Connection connection) {
        if (connection == null) {
            throw new NonTransactionInvocationException("Can't invoke DAO method outside of 'doInTransaction'");
        }
    }
}
