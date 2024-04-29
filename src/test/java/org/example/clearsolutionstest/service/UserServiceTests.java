package org.example.clearsolutionstest.service;

import org.example.clearsolutionstest.dto.*;
import org.example.clearsolutionstest.exception.RegistrationException;
import org.example.clearsolutionstest.mapper.UserMapper;
import org.example.clearsolutionstest.mapper.UserMapperImpl;
import org.example.clearsolutionstest.model.User;
import org.example.clearsolutionstest.repository.UserRepository;
import org.example.clearsolutionstest.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Test register new  user")
    public void testRegisterNewValidUser() {
        RequestUserDto requestUserDto = new RequestUserDto(
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "email@mail.com",
                "1234567890",
                "123456654"

        );
        User user = new User();
        user.setId(1L);
        user.setFirstName(requestUserDto.firstName());
        user.setLastName(requestUserDto.lastName());
        user.setDateOfBirth(requestUserDto.dateOfBirth());
        user.setEmail(requestUserDto.email());
        user.setAddress(requestUserDto.address());
        user.setPhoneNumber(requestUserDto.phoneNumber());

        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseUserDto responseUserDto = userService.register(requestUserDto);

        assertThat(responseUserDto)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "John")
                .hasFieldOrPropertyWithValue("lastName", "Doe")
                .hasFieldOrPropertyWithValue("dateOfBirth", LocalDate.of(1990, 1, 1))
                .hasFieldOrPropertyWithValue("email", "email@mail.com")
                .hasFieldOrPropertyWithValue("address", "1234567890")
                .hasFieldOrPropertyWithValue("phoneNumber", "123456654");


    }

    @Test
@DisplayName("Test search by birth date range")
public void shouldSearchByBirthDateRange() {
    // Arrange
    RequestSearchByDateDto searchByDateDto = new RequestSearchByDateDto(
            LocalDate.of(1990, 1, 1),
            LocalDate.of(2003, 1, 2));

    User user1 = new User();
    user1.setDateOfBirth(LocalDate.of(1995, 5, 5));

    User user2 = new User();
    user2.setDateOfBirth(LocalDate.of(2000, 5, 5));

    User user3 = new User();
    user3.setDateOfBirth(LocalDate.of(1985, 5, 5));

    User user4 = new User();
    user4.setDateOfBirth(LocalDate.of(2005, 5, 5));

    List<User> users = List.of(user1, user2, user3, user4);

    when(userRepository.findByBirthDateBetween(searchByDateDto.StartDate(), searchByDateDto.EndDate()))
            .thenReturn(users.stream()
                    .filter(user -> !user.getDateOfBirth().isBefore(searchByDateDto.StartDate()) &&
                            !user.getDateOfBirth().isAfter(searchByDateDto.EndDate()))
                    .toList());

    // Act
    List<ResponseUserDto> actualResponseUserDtos = userService.searchByBirthDateRange(searchByDateDto);

    // Assert
    verify(userRepository, times(1)).findByBirthDateBetween(searchByDateDto.StartDate(), searchByDateDto.EndDate());
    assertEquals(2, actualResponseUserDtos.size());
}

    @Test
    @DisplayName("Test update one user")
    public void testUpdateUser() {
        UpdateUserDto Dto = new UpdateUserDto(
                1L,  "John", "Doe", "john@example.com",
                LocalDate.of(1990, 5, 15), "Address", "1234567890");

        List<UpdateUserDto> updateUserDto  = new ArrayList<>();
        updateUserDto.add(Dto);

        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        users.add(user1);
        User user2 = new User();
        user2.setId(2L);
        users.add(user2);

        when(userRepository.findById(updateUserDto.get(0).id())).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(userMapper.toResponseUserDto(any(User.class))).thenReturn(new ResponseUserDto(
                1L, "John", "Doe", LocalDate.of(1990, 5, 15),
                "john@example.com", "Address", "1234567890"));

        List<ResponseUserDto> result = userService.update(updateUserDto);;
        assertEquals("John", result.get(0).firstName());
        verify(userRepository, times(1)).findById(updateUserDto.get(0).id());
        verify(userMapper, times(1)).updateUserFromDto(updateUserDto.get(0), user1);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toResponseUserDto(any(User.class));
    }

 @Test
@DisplayName("Test update several users")
public void testUpdateMultipleUsers() {
    UpdateUserDto userDto1 = new UpdateUserDto(1L, "John", "Doe", "john@example.com",
            LocalDate.of(1990, 5, 15), "Address", "1234567890");
    UpdateUserDto userDto2 = new UpdateUserDto(3L, "Jane", "Doe", "jane@example.com",
            LocalDate.of(2000, 5, 15), "Address", "1234567891");
    List<UpdateUserDto> updateUserDtos = List.of(userDto1, userDto2);

    List<User> users = new ArrayList<>();
    User user1 = new User();
    user1.setId(1L);
    users.add(user1);
    User user2 = new User();
    user2.setId(2L);
    users.add(user2);
    User user3 = new User();
    user3.setId(3L);
    users.add(user3);

    when(userRepository.findById(userDto1.id())).thenReturn(Optional.of(user1));
    when(userRepository.findById(userDto2.id())).thenReturn(Optional.of(user2));
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    when(userMapper.toResponseUserDto(any(User.class))).thenAnswer(invocation -> {
        User user = invocation.getArgument(0);
        return new ResponseUserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(),
                user.getEmail(), user.getAddress(), user.getPhoneNumber());
    });

    List<ResponseUserDto> result = userService.update(updateUserDtos);

    assertEquals(2, result.size());
    assertEquals(userDto1.firstName(), result.get(0).firstName());
    assertEquals(userDto2.firstName(), result.get(1).firstName());

    verify(userRepository, times(1)).findById(userDto1.id());
    verify(userRepository, times(1)).findById(userDto2.id());
    verify(userMapper, times(1)).updateUserFromDto(userDto1, user1);
    verify(userMapper, times(1)).updateUserFromDto(userDto2, user2);
    verify(userRepository, times(2)).save(any(User.class));
    verify(userMapper, times(2)).toResponseUserDto(any(User.class));
}

    @Test
    @DisplayName("Test update all users")
    public void testUpdateAllUsers() {
        UpdateAllUserDto updateAllUserDto = new UpdateAllUserDto(
                "John", "Doe", "john@example.com",
                LocalDate.of(1990, 5, 15), "Address", "1234567890");
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userMapper.toResponseUserDto(any(User.class))).thenReturn(new ResponseUserDto(
                null, "John", "Doe", LocalDate.of(1990, 5, 15),
                "john@example.com", "Address", "1234567890"));

        List<ResponseUserDto> result = userService.updateAll(updateAllUserDto);

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).updateUserFromDto(updateAllUserDto, users.get(0));
        verify(userRepository, times(2)).save(any(User.class));
        verify(userMapper, times(2)).toResponseUserDto(any(User.class));

    }

    @Test
    @DisplayName("Test delete user")
    public void testDeleteUser() {

        Long id = 1L;
        doNothing().when(userRepository).deleteById(id);
        userService.delete(id);
        verify(userRepository, times(1)).deleteById(id);
    }

}
