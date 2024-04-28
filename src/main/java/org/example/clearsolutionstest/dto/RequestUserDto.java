package org.example.clearsolutionstest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.example.clearsolutionstest.annotation.minage.MinAge;

import java.time.LocalDate;

public record RequestUserDto(
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @Past
        @MinAge
        @NotNull
        LocalDate dateOfBirth,
        @Email
        String email,
        String address,
        String phoneNumber
) {
}