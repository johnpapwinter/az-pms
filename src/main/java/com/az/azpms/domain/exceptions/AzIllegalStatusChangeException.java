package com.az.azpms.domain.exceptions;

public class AzIllegalStatusChangeException extends RuntimeException {

    public AzIllegalStatusChangeException() {
        super();
    }

    public AzIllegalStatusChangeException(String message) {
        super(message);
    }
}
