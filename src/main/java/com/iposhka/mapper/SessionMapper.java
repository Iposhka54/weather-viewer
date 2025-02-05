package com.iposhka.mapper;

import com.iposhka.dto.SessionDto;
import com.iposhka.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface SessionMapper {

    Session toEntity(SessionDto dto);

    SessionDto toDto(Session entity);
}