package com.dreamwheels.dreamwheels.configuration.middleware;

import com.dreamwheels.dreamwheels.configuration.exceptions.CustomException;
import com.dreamwheels.dreamwheels.configuration.exceptions.ValidationException;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@Component
@Aspect
public class TryCatchMiddleware {

    @SneakyThrows
    @Around("@annotation(com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation)")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            if (result instanceof ResponseEntity) {
                ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
                if (responseEntity.getStatusCode().isError()) {
                    return responseEntity; // Return without caching
                }
            }
            return result;
        } catch (CustomException customException) {
            return handleError(customException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException notFoundException) {
            // Rethrow the exception to prevent caching
            throw notFoundException;
//            return handleError(notFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return handleError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<GarageApiResponse<Object>> handleError(String message, HttpStatus status) {
        return new ResponseEntity<>(new GarageApiResponse<>(null, message, ResponseType.ERROR), status);
    }
}
