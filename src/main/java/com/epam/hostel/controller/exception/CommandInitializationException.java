package com.epam.hostel.controller.exception;

/**
 * Exception, which will be thrown when occurred
 * some fail in commands initialization
 */
public class CommandInitializationException extends RuntimeException {
    private static long serialVersionUID = 1L;

    public CommandInitializationException() {
    }

    public CommandInitializationException(String message) {
        super(message);
    }

    public CommandInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandInitializationException(Throwable cause) {
        super(cause);
    }
}
