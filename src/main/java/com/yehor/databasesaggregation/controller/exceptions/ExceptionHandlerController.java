package com.yehor.databasesaggregation.controller.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final String ERROR_MESSAGE = "Something went wrong. Please wait a minute we are going to fix everything.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleAuthorizationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Handle bad request error", ex);

        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAuthorizationException(Exception ex, WebRequest request) {
        log.error("Handle internal server error", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(String.format("Response code is %d. %s The problem is: %s",
                        INTERNAL_SERVER_ERROR,
                        ERROR_MESSAGE,
                        ex.getMessage())
                );
    }
}
