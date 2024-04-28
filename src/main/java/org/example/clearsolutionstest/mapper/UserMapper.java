package org.example.clearsolutionstest.mapper;

import org.example.clearsolutionstest.dto.RequestUserDto;
import org.example.clearsolutionstest.dto.ResponseUserDto;
import org.example.clearsolutionstest.dto.UpdateAllUserDto;
import org.example.clearsolutionstest.dto.UpdateUserDto;
import org.mapstruct.*;
import org.example.clearsolutionstest.model.User;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    User toUser(RequestUserDto requestUserDto);

    @Mapping(source = "firstName", target = "firstName")
    ResponseUserDto toResponseUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    void updateUserFromDto(UpdateUserDto requestUserDto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    void updateUserFromDto(UpdateAllUserDto requestUserDto, @MappingTarget User user);

}
