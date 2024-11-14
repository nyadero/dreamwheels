package com.dreamwheels.dreamwheels.configuration.middleware;

import com.dreamwheels.dreamwheels.configuration.exceptions.*;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class TryCatchMiddleware {

    @Around("@annotation(com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation)")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            if (result instanceof ResponseEntity<?> responseEntity) {
                if (responseEntity.getStatusCode().isError()) {
                    return responseEntity; // Return without caching
                }
            }
            return result;
        } catch (EntityNotFoundException | EntityExistsException | TokenExpiredException | ValidationException |
                 BadCredentialsException | CustomException | AccessDeniedException | PasswordsNotMatchingException exception
        ) {
            // Rethrow the exception to prevent caching
            throw exception;
        } catch (Exception exception) {
            log.info(exception.getMessage());
            return handleError(exception.getMessage());
        }
    }

    private ResponseEntity<GarageApiResponse<Object>> handleError(String message) {
        return ResponseEntity.internalServerError().body(new GarageApiResponse<>(null, message, ResponseType.ERROR));
    }
}
