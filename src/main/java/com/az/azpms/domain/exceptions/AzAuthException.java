package com.az.azpms.domain.exceptions;

public class AzAuthException extends RuntimeException {

    public AzAuthException() {
        super();
    }

    public AzAuthException(String message) {
        super(message);
    }
}
