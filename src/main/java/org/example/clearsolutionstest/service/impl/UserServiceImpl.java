package org.example.clearsolutionstest.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.clearsolutionstest.dto.RequestSearchByDateDto;
import org.example.clearsolutionstest.dto.RequestUserDto;
import org.example.clearsolutionstest.dto.ResponseUserDto;
import org.example.clearsolutionstest.dto.UpdateUserDto;
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
    public ResponseUserDto register(RequestUserDto userRegistrationRequest) {
        User user = userMapper.toUser(userRegistrationRequest);
        System.out.println("user: " + user);
        return userMapper.toResponseUserDto(userRepository.save(user));
    }

    @Override
    public ResponseUserDto update(UpdateUserDto userUpdateRequest) {

        return null;
    }



    @Override
    public List<ResponseUserDto> searchByBirthDateRange(RequestSearchByDateDto request) {
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
