package org.example.clearsolutionstest.service;

import org.example.clearsolutionstest.dto.*;

import java.util.List;

public interface UserService {
    ResponseUserDto register(RequestUserDto userRegistrationRequest);

    List<ResponseUserDto> update(List<UpdateUserDto> userUpdateRequests);

    List<ResponseUserDto> searchByBirthDateRange(RequestSearchByDateDto request);

    void delete(Long id);

    List<ResponseUserDto> updateAll(UpdateAllUserDto userUpdateRequest);
}
