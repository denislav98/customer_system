package com.amdocs.interview.web;

import com.amdocs.interview.service.exception.CustomerContactNotFoundException;
import com.amdocs.interview.service.exception.CustomerNotFoundException;
import com.amdocs.interview.service.exception.DuplicateContactMediumException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> badRequestException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(getErrorDetails(ex, request), BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateContactMediumException.class)
    public ResponseEntity<?> duplicateContactMediumException(DuplicateContactMediumException ex, WebRequest request) {
        return new ResponseEntity<>(getErrorDetails(ex, request), BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(getErrorDetails(ex, request), NOT_FOUND);
    }

    @ExceptionHandler(CustomerContactNotFoundException.class)
    public ResponseEntity<?> customerContactNotFoundException(CustomerContactNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(getErrorDetails(ex, request), NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        return new ResponseEntity<>(getErrorDetails(ex, request), INTERNAL_SERVER_ERROR);
    }

    private ErrorDetails getErrorDetails(Exception e, WebRequest request) {
        return new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false));
    }

    private static class ErrorDetails {
        private final Date timestamp;
        private final String message;
        private final String details;

        public ErrorDetails(Date timestamp, String message, String details) {
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getDetails() {
            return details;
        }
    }
}
