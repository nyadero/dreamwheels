package com.dreamwheels.dreamwheels.configuration.validation;

import com.dreamwheels.dreamwheels.configuration.middleware.EnumValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValidation, String> {
    private EnumValidation annotation;

    @Override
    public void initialize(EnumValidation constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true; // consider using @NotNull for null check instead
        }
        return Arrays.stream(annotation.enumClass().getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
