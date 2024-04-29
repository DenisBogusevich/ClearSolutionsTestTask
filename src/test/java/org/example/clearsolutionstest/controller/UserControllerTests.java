package org.example.clearsolutionstest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.clearsolutionstest.dto.*;
import org.example.clearsolutionstest.exception.DataRangeException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*    ;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:database/users/add-users.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/users/delete-users.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class UserControllerTests {

    protected static MockMvc mockMvc;
    @Autowired
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    }

    @Test
    @DisplayName("Create a new user")
    void create_ValidCase_ShouldReturnUserDto() throws Exception {
        RequestUserDto requestUserDto = new RequestUserDto(
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "email@mail.com",
                "1234567890",
                "123456654"

        );

        ResponseUserDto responseUserDto = new ResponseUserDto(
                1L,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "email@mail.com",
                "1234567890",
                "123456654");

        String jsonRequest = objectMapper.writeValueAsString(requestUserDto);

        MvcResult result = mockMvc.perform(post("/register")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        ResponseUserDto actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), ResponseUserDto.class);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertNotNull(actualResponse.id());
        EqualsBuilder.reflectionEquals(responseUserDto, actualResponse, "id");
    }

    @Test
    @DisplayName("Register invalid user")
    void create_InvalidCase_ShouldThrowRegistrationException() throws Exception {
        RequestUserDto requestUserDto = new RequestUserDto(
                "John",
                "Doe",
                LocalDate.of(2012, 1, 1),
                "email@mail.com",
                "1234567890",
                "123456654"

        );

        mockMvc.perform(post("/register")
                        .content(objectMapper.writeValueAsString(requestUserDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
                        result.getResolvedException()));
    }

    @Test
    @DisplayName("Search by birth date range")
    void searchByBirthDateRange_ValidCase_ShouldReturnListOfUserDto() throws Exception {
        // Arrange
        RequestSearchByDateDto requestSearchByDateDto = new RequestSearchByDateDto(
                LocalDate.of(1996, 1, 1),
                LocalDate.of(2003, 1, 2));

        List<ResponseUserDto> expectedResponse = new ArrayList<>();

        expectedResponse.add(new ResponseUserDto(
                2L,
                "Jane",
                "Doe",
                LocalDate.of(2000, 5, 5),
                "jane@example.com",
                "Address2",
                "0987654321"));


        // Act
        MvcResult result = mockMvc.perform(post("/search")
                        .content(objectMapper.writeValueAsString(requestSearchByDateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        List<ResponseUserDto> actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });



      assertEquals(expectedResponse.size(), actualResponse.size());

    }

    @Test
    @DisplayName("Search by reverse birth date range")
    void searchByBirthDateRange_InvalidCase_ThrowDataRangeException() throws Exception {
        // Arrange
        RequestSearchByDateDto requestSearchByDateDto = new RequestSearchByDateDto(
                LocalDate.of(2003, 1, 2),
                LocalDate.of(1996, 1, 1));

        mockMvc.perform(post("/search")
                        .content(objectMapper.writeValueAsString(requestSearchByDateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
                        result.getResolvedException()));
    }

    @Test
    @DisplayName("Update a user")
    void updateOneUser_ValidCase_ShouldReturnListOfUserDto() throws Exception {
        // Arrange
        List<UpdateUserDto> updateUserDtoList = new ArrayList<>();
        UpdateUserDto requestUserDto = new UpdateUserDto(
                1L,
                "John",
                "Doe",
                "email@mail.com",
                LocalDate.of(1990, 1, 1),
                "1234567890",
                "123456654"
        );
        updateUserDtoList.add(requestUserDto);

        ResponseUserDto responseUserDto = new ResponseUserDto(
                1L,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "email@mail.com",
                "1234567890",
                "123456654"
        );

        List<ResponseUserDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(responseUserDto);

        String jsonRequest = objectMapper.writeValueAsString(updateUserDtoList);

        // Act
        MvcResult result = mockMvc.perform(put("/update")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        List<ResponseUserDto> actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });

        assertEquals(expectedResponse.size(), actualResponse.size());
    }

    @Test
    @DisplayName("Update all users")
    void updateAllUsers_ValidCase_ShouldReturnListOfUserDto() throws Exception {
        // Arrange
        UpdateAllUserDto updateAllUserDto = new UpdateAllUserDto(
                "John",
                "Doe",
                "email@mail.com",
                LocalDate.of(1990, 1, 1),
                "1234567890",
                "123456654"
        );
        List<ResponseUserDto> expectedResponse = new ArrayList<>();
        ResponseUserDto r = new ResponseUserDto(
                1L,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "email@mail.com",
                "1234567890",
                "123456654"
        );
        expectedResponse.add(r);
        expectedResponse.add(r);
        expectedResponse.add(r);

        String jsonRequest = objectMapper.writeValueAsString(updateAllUserDto);

        MvcResult result = mockMvc.perform(put("/update/all")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<ResponseUserDto> actualResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });

        assertEquals(expectedResponse.size(), actualResponse.size());}


    @Test
    @DisplayName("Delete a user")
    void delete_ValidCase_ShouldReturnSuccessMessage() throws Exception {
        Long id = 1L;
        String expectedResponse = "User with id: " + id + " deleted successfully";

        MvcResult result = mockMvc.perform(delete("/delete/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();

        assertEquals(expectedResponse, actualResponse);
    }


}