package org.example.clearsolutionstest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record RequestUserDto(
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @Email
        String email,
        @Past
        @NotNull
        LocalDate dateOfBirth,
        String address,
        String phoneNumber
) {
}