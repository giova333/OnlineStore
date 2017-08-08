package com.gladunalexander.exceptions;

/**
 * @author Alexander Gladun
 */
public class OrderErrorException extends RuntimeException {

    public OrderErrorException() {
    }

    public OrderErrorException(String message) {
        super(message);
    }

    public OrderErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderErrorException(Throwable cause) {
        super(cause);
    }
}
