package com.task.cafemanager.api.facade.errors;

import com.task.cafemanager.exceptions.DuplicateAccountException;
import com.task.cafemanager.exceptions.OrderStatusStillOpenException;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundException(ResourceNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity duplicateAccountFound(DuplicateAccountException exception) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderStatusStillOpenException.class)
    public ResponseEntity orderStatusStillOpenException(OrderStatusStillOpenException exception) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessDeniedExceptionHandler(AccessDeniedException exception) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException exception) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, exception.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity globalExceptionHandler(Exception exception) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
