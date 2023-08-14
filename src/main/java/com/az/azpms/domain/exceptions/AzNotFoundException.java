package com.az.azpms.domain.exceptions;

public class AzNotFoundException extends RuntimeException {

    public AzNotFoundException() {
        super();
    }

    public AzNotFoundException(String message) {
        super(message);
    }
}
