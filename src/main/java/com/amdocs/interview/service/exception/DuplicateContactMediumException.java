package com.amdocs.interview.service.exception;

public class DuplicateContactMediumException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateContactMediumException(String message, Throwable cause) {
        super(message, cause);
    }
}
