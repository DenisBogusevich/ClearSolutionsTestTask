package org.example.clearsolutionstest.annotation.minage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

    @Component
    public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {

        @Value("${minimum.age}")
        private int minAge;

        @Override
        public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
            if (dateOfBirth == null) {
                return true;
            }
            return Period.between(dateOfBirth, LocalDate.now()).getYears() >= minAge;
        }
    }
