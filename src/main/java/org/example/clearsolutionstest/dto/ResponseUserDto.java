package org.example.clearsolutionstest.dto;

import java.time.LocalDate;

public record ResponseUserDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String address,
        String phoneNumber
) {

}
