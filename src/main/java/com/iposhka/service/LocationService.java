package com.iposhka.service;

import com.iposhka.dto.GeoResponceDto;
import com.iposhka.dto.LocationResponseDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.dto.WeatherResponceDto;
import com.iposhka.mapper.LocationMapper;
import com.iposhka.mapper.UserMapper;
import com.iposhka.model.Location;
import com.iposhka.model.User;
import com.iposhka.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class LocationService {
    private static final int LIMIT = 5;
    private final WeatherApiService weatherApiService;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final UserMapper userMapper;

    public LocationService(WeatherApiService weatherApiService, LocationRepository locationRepository, LocationMapper locationMapper, UserMapper userMapper) {
        this.weatherApiService = weatherApiService;
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.userMapper = userMapper;
    }

    public List<GeoResponceDto> getCoordinatesByLocation(String location){
        if(location == null || location.isBlank()){
            return Collections.emptyList();
        }

        return weatherApiService.getGeoCoordinates(location, LIMIT);
    }

    public List<WeatherResponceDto> getWeatherByLocations(List<LocationResponseDto> locations){
        if(locations.isEmpty()){
            return Collections.emptyList();
        }

        List<WeatherResponceDto> weather = new ArrayList<>();

        for (var location : locations) {
            WeatherResponceDto weatherLocation = weatherApiService.getWeatherByLocations(location);
            weatherLocation.setLocationId(location.getId());
            weatherLocation.setName(location.getName());
            weather.add(weatherLocation);
        }

        return weather;
    }

    @Transactional
    public void addLocation(BigDecimal lat, BigDecimal lon, String name, UserLoginDto userDto){
        LocationResponseDto locationDto = LocationResponseDto.builder()
                .name(name)
                .longitude(lon)
                .latitude(lat)
                .build();

        Location location = locationMapper.toEntity(locationDto);
        User user = userMapper.toEntity(userDto);
        location.setUser(user);

        locationRepository.save(location);
    }

    @Transactional
    public void deleteLocation(int locationId, int userId){
        locationRepository.deleteByIdAndUserId(locationId, userId);
    }

    public void removeDuplicatesLocations(List<GeoResponceDto> responceLocations, List<LocationResponseDto> userLocations) {
        Iterator<GeoResponceDto> iterator = responceLocations.iterator();
        while(iterator.hasNext()){
            var resLocation = iterator.next();
            for (var userLocation : userLocations) {
                if(isDublicateLocation(userLocation, resLocation)){
                    iterator.remove();
                    break;
                }
            }
        }
    }

    private boolean isDublicateLocation(LocationResponseDto userLocation, GeoResponceDto resLocation) {
        return userLocation.getLatitude().compareTo(resLocation.getLatitude()) == 0 &&
                userLocation.getLongitude().compareTo(resLocation.getLongitude()) == 0;
    }
}