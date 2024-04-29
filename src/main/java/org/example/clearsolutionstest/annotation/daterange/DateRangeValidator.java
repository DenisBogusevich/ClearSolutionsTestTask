package org.example.clearsolutionstest.annotation.daterange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.clearsolutionstest.dto.RequestSearchByDateDto;
import org.example.clearsolutionstest.exception.DataRangeException;

public class DateRangeValidator implements ConstraintValidator<DateRange, RequestSearchByDateDto> {

    @Override
    public boolean isValid(RequestSearchByDateDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.StartDate().isBefore(value.EndDate());
    }
}