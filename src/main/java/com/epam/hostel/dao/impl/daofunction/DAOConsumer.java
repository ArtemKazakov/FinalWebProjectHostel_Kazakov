package com.epam.hostel.dao.impl.daofunction;

import java.sql.SQLException;

/**
 * Represents an operation that accepts a single input argument and returns no result.
 *
 * @param <T> the type of the input to the operation
 */
@FunctionalInterface
public interface DAOConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws SQLException if occurred some error with SQL
     */
    void accept(T t) throws SQLException;
}
