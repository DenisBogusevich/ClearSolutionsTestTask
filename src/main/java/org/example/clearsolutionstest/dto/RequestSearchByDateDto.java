package org.example.clearsolutionstest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RequestSearchByDateDto(
        @NotNull
        LocalDate StartDate,
        @NotNull
        LocalDate EndDate
) {
}
