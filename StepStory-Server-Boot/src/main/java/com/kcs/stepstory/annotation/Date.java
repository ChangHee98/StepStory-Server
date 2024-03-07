package com.kcs.stepstory.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.kcs.stepstory.constants.LocalDateValidator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Date {
    String message() default "Invalid Date Format. (yyyy-MM-dd)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

