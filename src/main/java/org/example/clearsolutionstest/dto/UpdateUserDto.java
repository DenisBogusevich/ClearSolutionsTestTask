package org.example.clearsolutionstest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record UpdateUserDto(
        @NotNull
        Long id,
        String firstName,
        String lastName,
        @Email
        String email,
        @Past
        LocalDate dateOfBirth,
        String address,
        String phoneNumber) {
}
