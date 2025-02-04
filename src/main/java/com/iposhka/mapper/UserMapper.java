package com.iposhka.mapper;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.UserDto;
import com.iposhka.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(CreateUserDto dto);

    User toEntity(UserDto dto);
}