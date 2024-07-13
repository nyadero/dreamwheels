package com.dreamwheels.dreamwheels.configuration.middleware;

import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.exceptions.ValidationException;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
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
    public ResponseEntity<GarageApiResponse> handleException(ProceedingJoinPoint proceedingJoinPoint){
        try{
            return (ResponseEntity<GarageApiResponse>) proceedingJoinPoint.proceed();
        } catch(EntityNotFoundException entityNotFoundException){
            System.out.println(entityNotFoundException.getMessage());
            return new ResponseEntity<>(new GarageApiResponse(null, entityNotFoundException.getMessage(), ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        } catch (ValidationException validationException) {
            return new ResponseEntity<>(new GarageApiResponse(null, validationException.getMessage() + " here",
                    ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }catch (HttpServerErrorException.InternalServerError error){
            return new ResponseEntity<>(new GarageApiResponse(null, error.getMessage() + " here",
                    ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }
}
