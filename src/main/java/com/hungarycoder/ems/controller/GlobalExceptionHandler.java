package com.hungarycoder.ems.controller;

import com.hungarycoder.ems.model.errorhandling.ErrorResponseDTO;
import com.hungarycoder.ems.model.errorhandling.ValidationErrorResponseDTO;
import com.hungarycoder.ems.model.exception.EmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error(ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        logger.error(ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponseDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("validation error: ", ex);
        List<ValidationErrorResponseDTO> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(this::mapFieldErrorToValidationErrorResponse)
            .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
        logger.error("An unexpected error occurred: ", ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ValidationErrorResponseDTO mapFieldErrorToValidationErrorResponse(FieldError fieldError) {
        return new ValidationErrorResponseDTO(fieldError.getField(), fieldError.getDefaultMessage());
    }
}