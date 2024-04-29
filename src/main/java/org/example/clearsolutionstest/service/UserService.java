package org.example.clearsolutionstest.service;

import org.example.clearsolutionstest.dto.*;
import org.example.clearsolutionstest.exception.DataRangeException;
import org.example.clearsolutionstest.exception.RegistrationException;
import org.example.clearsolutionstest.exception.UpdateException;

import java.util.List;

public interface UserService {
    ResponseUserDto register(RequestUserDto userRegistrationRequest) throws RegistrationException;

    List<ResponseUserDto> update(List<UpdateUserDto> userUpdateRequests) throws UpdateException;

    List<ResponseUserDto> searchByBirthDateRange(RequestSearchByDateDto request) throws DataRangeException;

    void delete(Long id);

    List<ResponseUserDto> updateAll(UpdateAllUserDto userUpdateRequest) throws UpdateException;
}
