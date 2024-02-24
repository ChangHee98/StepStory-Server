package org.donnguk.emodiary.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.donnguk.emodiary.contrant.LocalDateValidator;

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

