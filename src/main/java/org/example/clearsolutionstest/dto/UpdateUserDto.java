package org.example.clearsolutionstest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public record UpdateUserDto (
        List<Long> ids,
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @NotEmpty
        @Email
        String email,
        @Past
        @NotEmpty
        LocalDate birthday,
        String address,
        String phoneNumber) {
}
