package org.example.clearsolutionstest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UpdateAllUserDto(
        String firstName,
        String lastName,
        @Email
        String email,
        @Past
        LocalDate dateOfBirth,
        String address,
        String phoneNumber) {
}
