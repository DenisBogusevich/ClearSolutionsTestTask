package org.example.clearsolutionstest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.clearsolutionstest.dto.RequestSearchByDateDto;
import org.example.clearsolutionstest.dto.RequestUserDto;
import org.example.clearsolutionstest.dto.ResponseUserDto;
import org.example.clearsolutionstest.dto.UpdateUserDto;
import org.example.clearsolutionstest.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseUserDto register(
            @RequestBody @Valid RequestUserDto userRegistrationRequest) {
        System.out.printf("userRegistrationRequest: %s%n", userRegistrationRequest);
        return userService.register(userRegistrationRequest);
    }


    @PostMapping("/search")
    public List<ResponseUserDto> searchByBirthDateRange(
            @RequestBody @Valid RequestSearchByDateDto request) {
        return userService.searchByBirthDateRange(request);
    }

    @PutMapping("/update")
    public ResponseUserDto update(
            @RequestBody @Valid UpdateUserDto userUpdateRequest) {
        return userService.update(userUpdateRequest);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }


}
