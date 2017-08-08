package com.gladunalexander.exceptions;

/**
 * @author Alexander Gladun
 */
public class UserIdMismatchException extends RuntimeException {

    public UserIdMismatchException() {
    }

    public UserIdMismatchException(String message) {
        super(message);
    }

    public UserIdMismatchException(Throwable cause) {
        super(cause);
    }
}
