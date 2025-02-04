package com.iposhka.mapper;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.UserDto;
import com.iposhka.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "username", target = "login")
    User toEntity(CreateUserDto dto);

    @Mapping(source = "username", target = "login")
    User toEntity(UserDto dto);
}