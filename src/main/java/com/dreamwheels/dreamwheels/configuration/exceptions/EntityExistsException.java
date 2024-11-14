package com.dreamwheels.dreamwheels.configuration.exceptions;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String message) {
        super(message);
    }
}
