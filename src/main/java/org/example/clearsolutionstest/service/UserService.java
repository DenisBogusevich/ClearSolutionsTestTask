package org.example.clearsolutionstest.service;

import org.example.clearsolutionstest.dto.RequestSearchByDateDto;
import org.example.clearsolutionstest.dto.RequestUserDto;
import org.example.clearsolutionstest.dto.ResponseUserDto;
import org.example.clearsolutionstest.dto.UpdateUserDto;

import java.util.List;

public interface UserService {
    ResponseUserDto register(RequestUserDto userRegistrationRequest);

    ResponseUserDto update(UpdateUserDto userUpdateRequest);



    List<ResponseUserDto> searchByBirthDateRange(RequestSearchByDateDto request);

    void delete(Long id);
}
