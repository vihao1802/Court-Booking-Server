package com.court_booking_project.court_booking_server.custom_annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)

public @interface PasswordConstraint  {
    String message() default "Invalid Password";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
