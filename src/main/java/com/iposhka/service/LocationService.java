package com.iposhka.service;

import com.iposhka.dto.GeoResponceDto;
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
}
