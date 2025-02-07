package com.iposhka.service;

import com.iposhka.dto.GeoResponceDto;
import com.iposhka.dto.LocationResponseDto;
import com.iposhka.dto.WeatherResponceDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LocationService {
    private static final int LIMIT = 5;
    private final WeatherApiService weatherApiService;

    public LocationService(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
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
            weather.add(weatherApiService.getWeatherByLocations(location));
        }

        return weather;
    }
}
