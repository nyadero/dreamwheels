package com.dreamwheels.dreamwheels.configuration.exceptions;

public class PasswordsNotMatchingException extends RuntimeException {
    public PasswordsNotMatchingException(String message) {
        super(message);
    }
}
