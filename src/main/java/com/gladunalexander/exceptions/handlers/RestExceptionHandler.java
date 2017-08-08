package com.gladunalexander.exceptions.handlers;

import com.gladunalexander.exceptions.*;
import com.stripe.exception.StripeException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Alexander Gladun
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFound(Exception ex, WebRequest request){
        return handleExceptionInternal(ex, "User not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<Object> handleProductNotFound(Exception ex, WebRequest request){
        return handleExceptionInternal(ex, "Product not found",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({UserIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
            DuplicateUserException.class})
    public ResponseEntity<Object> handleUserBadRequest(Exception ex, WebRequest request){
        return handleExceptionInternal(ex, "Bad request",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({StripeException.class, OrderErrorException.class})
    public ResponseEntity<Object> handleCheckoutEror(Exception ex, WebRequest request){
        return handleExceptionInternal(ex, "Checkout Error",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
