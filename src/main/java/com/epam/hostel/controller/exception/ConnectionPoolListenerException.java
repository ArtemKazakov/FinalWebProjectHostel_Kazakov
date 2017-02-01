package com.epam.hostel.controller.exception;

public class ConnectionPoolListenerException extends RuntimeException {
    public ConnectionPoolListenerException(String message) {
        super(message);
    }

    public ConnectionPoolListenerException(String message, Throwable cause) {
        super(message, cause);
    }
}
