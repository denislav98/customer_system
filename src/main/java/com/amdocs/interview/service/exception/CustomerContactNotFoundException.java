package com.amdocs.interview.service.exception;

public class CustomerContactNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerContactNotFoundException(String message) {
        super(message);
    }
}
