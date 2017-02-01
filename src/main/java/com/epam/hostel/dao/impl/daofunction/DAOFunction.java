package com.epam.hostel.dao.impl.daofunction;

import java.sql.SQLException;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface DAOFunction<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws SQLException if occurred some error with SQL
     */
    R apply(T t) throws SQLException;

}
