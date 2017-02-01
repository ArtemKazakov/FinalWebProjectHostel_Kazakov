package com.epam.hostel.dao.exception;

import com.epam.hostel.dao.transaction.TransactionManager;
/**
 * Exception that connected with {@link TransactionManager} work.
 */
public class NonTransactionInvocationException extends RuntimeException {
    private static long serialVersionUID = 1L;

    public NonTransactionInvocationException() {
    }

    public NonTransactionInvocationException(String message) {
        super(message);
    }

    public NonTransactionInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonTransactionInvocationException(Throwable cause) {
        super(cause);
    }
}
