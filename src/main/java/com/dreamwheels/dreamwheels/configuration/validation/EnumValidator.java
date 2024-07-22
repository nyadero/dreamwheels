package com.dreamwheels.dreamwheels.configuration.validation;

import com.dreamwheels.dreamwheels.configuration.middleware.EnumValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;
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

        if (annotation.enumClass().isEnum()) {
            Enum<?>[] enumValues = (Enum<?>[]) annotation.enumClass().getEnumConstants();
            System.out.println(Arrays.toString(enumValues));

            for (Enum<?> enumValue : enumValues) {
                if (enumValue.name().equalsIgnoreCase(value)) {
                    return true;
                }

                // Check if getDisplayName method exists and use it if available
                try {
                    Method getDisplayNameMethod = enumValue.getClass().getMethod("getDisplayName");
                    String displayName = (String) getDisplayNameMethod.invoke(enumValue);
                    if (displayName.equalsIgnoreCase(value)) {
                        return true;
                    }
                } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                    // Method does not exist or could not be accessed/invoked, so skip
                    System.out.println("Skipped");
                }
            }
        }

        return false;
    }
}
