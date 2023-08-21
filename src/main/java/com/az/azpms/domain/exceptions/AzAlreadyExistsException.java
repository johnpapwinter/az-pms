package com.az.azpms.domain.exceptions;

public class AzAlreadyExistsException extends RuntimeException {

    public AzAlreadyExistsException() {
        super();
    }

    public AzAlreadyExistsException(String message) {
        super(message);
    }
}
