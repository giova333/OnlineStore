package com.gladunalexander.exceptions;

/**
 * @author Alexander Gladun
 */
public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
    }

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(Throwable cause) {
        super(cause);
    }
}
