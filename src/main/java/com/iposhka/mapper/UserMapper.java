package com.iposhka.mapper;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "login")
    User toEntity(CreateUserDto dto);

    @Mapping(source = "username", target = "login")
    User toEntity(UserLoginDto dto);

    @Mapping(source = "login", target = "username")
    UserLoginDto toDto(User entity);
}