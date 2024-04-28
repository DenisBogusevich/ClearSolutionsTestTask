package org.example.clearsolutionstest.dto;

import jakarta.validation.constraints.NotNull;
import org.example.clearsolutionstest.annotation.daterange.DateRange;
import java.time.LocalDate;

@DateRange
public record RequestSearchByDateDto(
        @NotNull
        LocalDate StartDate,
        @NotNull
        LocalDate EndDate
) {
}
