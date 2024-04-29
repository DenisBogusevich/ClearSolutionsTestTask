package org.example.clearsolutionstest.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.clearsolutionstest.dto.*;
import org.example.clearsolutionstest.exception.DataRangeException;
import org.example.clearsolutionstest.exception.RegistrationException;
import org.example.clearsolutionstest.exception.UpdateException;
import org.example.clearsolutionstest.mapper.UserMapper;
import org.example.clearsolutionstest.model.User;
import org.example.clearsolutionstest.repository.UserRepository;
import org.example.clearsolutionstest.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseUserDto register(RequestUserDto userRegistrationRequest) throws RegistrationException {
        User user = userMapper.toUser(userRegistrationRequest);
        return userMapper.toResponseUserDto(userRepository.save(user));
    }

    @Override
    public List<ResponseUserDto> update(List<UpdateUserDto> userUpdateRequests) throws UpdateException {

        return userUpdateRequests.stream()
                .map(userUpdateRequest -> {
                    User user = userRepository.findById(userUpdateRequest.id())
                            .orElseThrow(() -> new RuntimeException("User not found with id: " + userUpdateRequest.id()));
                    userMapper.updateUserFromDto(userUpdateRequest, user);
                    User updatedUser = userRepository.save(user);
                    return userMapper.toResponseUserDto(updatedUser);
                })
                .collect(Collectors.toList());


    }

    @Override
    public List<ResponseUserDto> updateAll(UpdateAllUserDto userUpdateRequest) throws UpdateException{
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            userMapper.updateUserFromDto(userUpdateRequest, user);
            userRepository.save(user);
        });
        return users.stream()
                .map(userMapper::toResponseUserDto)
                .collect(Collectors.toList());


    }

    @Override
    public List<ResponseUserDto> searchByBirthDateRange(RequestSearchByDateDto request) throws DataRangeException {
        LocalDate startDate = request.StartDate();
        LocalDate endDate = request.EndDate();
        List<User> users = userRepository.findByBirthDateBetween(startDate, endDate);
        return users.stream()
                .map(userMapper::toResponseUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

    }


}
