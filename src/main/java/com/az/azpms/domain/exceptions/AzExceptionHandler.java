package com.az.azpms.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class AzExceptionHandler {

    @ExceptionHandler(AzIllegalStatusChangeException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalStatusChange(AzIllegalStatusChangeException e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(e.getMessage(), new ArrayList<>());
        e.printStackTrace();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AzNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(AzNotFoundException e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(e.getMessage(), new ArrayList<>());
        e.printStackTrace();

        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AzAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlreadyFoundException(AzAlreadyExistsException e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(e.getMessage(), new ArrayList<>());
        e.printStackTrace();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleFieldValidationException(MethodArgumentNotValidException e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();

        responseDTO.setErrorMessage(AzErrorMessages.CONSTRAINT_VALIDATION_ERROR.name());
        responseDTO.setValidationErrors(new ArrayList<>());
        e.getFieldErrors().forEach(
                fieldError -> responseDTO.getValidationErrors().add(String.format(
                        "%s %s",
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                )));
        e.printStackTrace();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setErrorMessage(AzErrorMessages.BAD_CREDENTIALS.name());
        responseDTO.setValidationErrors(new ArrayList<>());
        e.printStackTrace();

        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception e) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setErrorMessage(" There was an error. Please contact your administrator");
        responseDTO.setValidationErrors(new ArrayList<>());
        e.printStackTrace();

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
