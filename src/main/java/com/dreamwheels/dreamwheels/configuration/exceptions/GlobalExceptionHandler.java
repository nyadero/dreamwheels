package com.dreamwheels.dreamwheels.configuration.exceptions;

import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // handle validation exception
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<GarageApiResponse<?>> handleValidationException(ValidationException ex) {
        GarageApiResponse<List<String>> response = new GarageApiResponse<>(new Data<>(ex.getErrors()), "Validation failed", ResponseType.ERROR);
        return ResponseEntity.badRequest().body(response);
    }

    // handle generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GarageApiResponse<String >> handleGenericException(Exception ex) {
        log.error("Something went wrong {}", ex.getMessage());
        GarageApiResponse<String> response = new GarageApiResponse<>(null, "Something went wrong! Try again later.",  ResponseType.ERROR);
        return ResponseEntity.internalServerError().body(response);
    }

    // handle resource/entity not found exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GarageApiResponse<?>> handleEntityNotFoundException(EntityNotFoundException ex) {
        GarageApiResponse<?> response = new GarageApiResponse<>(null, ex.getMessage(), ResponseType.ERROR);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // handle resource/entity not found exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GarageApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        GarageApiResponse<?> response = new GarageApiResponse<>(null, ex.getMessage(), ResponseType.ERROR);
        return ResponseEntity.badRequest().body(response);
    }

    // handle passwords do not match exception
    @ExceptionHandler(PasswordsNotMatchingException.class)
    public ResponseEntity<GarageApiResponse<String>> handlePasswordsNotMatchingException(Exception e){
        return ResponseEntity.badRequest().body(new GarageApiResponse<>(null, e.getMessage(), ResponseType.ERROR));
    }

    // handle token expired exceptions
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<GarageApiResponse<String>> handleTokenExpiredException(Exception ex){
        return ResponseEntity.badRequest().body(new GarageApiResponse<>(null, ex.getMessage(), ResponseType.ERROR));
    }

    // handle entity exists exception
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<GarageApiResponse<String>> handleEntityExistsException(Exception ex){
        return ResponseEntity.badRequest().body(new GarageApiResponse<>(null, ex.getMessage(), ResponseType.ERROR));
    }

    // handle bad credentials exception
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GarageApiResponse<String>> handleBadCredentialsException(Exception ex){
        return ResponseEntity.badRequest().body(new GarageApiResponse<>(null, ex.getMessage(), ResponseType.ERROR));
    }

}
