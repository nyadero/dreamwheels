package com.dreamwheels.dreamwheels.configuration.exceptions;

import lombok.AllArgsConstructor;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
