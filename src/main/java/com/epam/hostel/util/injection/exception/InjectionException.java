package com.epam.hostel.util.injection.exception;

/**
 * Exception, which will be thrown when occurred
 * error in dependency injection
 */
public class InjectionException extends RuntimeException {
    private static long serialVersionUID = 1L;

    public InjectionException() {
        super();
    }

    public InjectionException(String message) {
        super(message);
    }

    public InjectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InjectionException(Throwable cause) {
        super(cause);
    }
}
