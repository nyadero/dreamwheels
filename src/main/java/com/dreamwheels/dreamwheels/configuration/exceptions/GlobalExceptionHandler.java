package com.dreamwheels.dreamwheels.configuration.exceptions;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handle resource/entity not found exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GarageApiResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        GarageApiResponse response = new GarageApiResponse(null, ex.getMessage(), ResponseType.ERROR);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    // handle validation exception
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<GarageApiResponse> handleValidationException(ValidationException ex) {
        GarageApiResponse response = new GarageApiResponse(ex.getErrors(), "Validation failed", ResponseType.ERROR);
//        return ResponseEntity.badRequest().body(ex.getErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // handle generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GarageApiResponse> handleGenericException(Exception ex) {
        GarageApiResponse response = new GarageApiResponse(null, ex.getMessage(),  ResponseType.ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}