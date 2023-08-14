package com.az.azpms.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AzExceptionHandler {

    @ExceptionHandler(AzIllegalStatusChangeException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalStatusChange(AzIllegalStatusChangeException e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(e.getMessage());

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AzNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(AzNotFoundException e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(e.getMessage());

        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

}
