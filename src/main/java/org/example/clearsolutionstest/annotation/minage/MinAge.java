package org.example.clearsolutionstest.annotation.minage;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.FIELD})
@Constraint(validatedBy = MinAgeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
        String message() default "User is under the minimum age of";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};

}
