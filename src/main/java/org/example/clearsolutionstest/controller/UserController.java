package org.example.clearsolutionstest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.clearsolutionstest.dto.*;
import org.example.clearsolutionstest.exception.RegistrationException;
import org.example.clearsolutionstest.exception.UpdateException;
import org.example.clearsolutionstest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for managing books")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDto register(
            @RequestBody @Valid RequestUserDto userRegistrationRequest) throws RegistrationException {
        return userService.register(userRegistrationRequest);
    }

    @Operation(summary = "Search users by birth date range")
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseUserDto> searchByBirthDateRange(
            @RequestBody @Valid RequestSearchByDateDto request) {
        return userService.searchByBirthDateRange(request);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseUserDto> update(
            @RequestBody @Valid List<UpdateUserDto> userUpdateRequests)
            throws UpdateException {
        return userService.update(userUpdateRequests);
    }

    @Operation(summary = "Update all users")
    @PutMapping("/update/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseUserDto> updateAll(
            @RequestBody @Valid UpdateAllUserDto userUpdateRequest) {
        return userService.updateAll(userUpdateRequest);
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "User with id: " + id + " deleted successfully";
    }


}
