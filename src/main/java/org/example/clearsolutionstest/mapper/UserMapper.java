package org.example.clearsolutionstest.mapper;

import org.example.clearsolutionstest.dto.RequestUserDto;
import org.example.clearsolutionstest.dto.ResponseUserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.example.clearsolutionstest.model.User;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    User toUser(RequestUserDto requestUserDto);

    ResponseUserDto toResponseUserDto(User user);

}
