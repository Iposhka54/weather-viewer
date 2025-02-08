package com.iposhka.mapper;

import com.iposhka.dto.LocationResponseDto;
import com.iposhka.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "name", target = "name")
    Location toEntity(LocationResponseDto dto);

    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "name", target = "name")
    LocationResponseDto toDto(Location entity);
}
