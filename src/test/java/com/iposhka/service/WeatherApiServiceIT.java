package com.iposhka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iposhka.config.TestConfig;
import com.iposhka.dto.GeoResponceDto;
import com.iposhka.dto.LocationResponseDto;
import com.iposhka.dto.WeatherResponceDto;
import com.iposhka.mapper.LocationMapper;
import com.iposhka.repository.LocationRepository;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
@SpringJUnitConfig(classes = TestConfig.class)
class WeatherApiServiceIT {
    @MockitoBean
    private WeatherApiService weatherApiService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocationService locationService;

    @BeforeEach
    @SneakyThrows
    void init(){
        List<GeoResponceDto> responce = List.of(
                GeoResponceDto.builder()
                        .name("Bryansk")
                        .latitude(BigDecimal.valueOf(53.2423778))
                        .longitude(BigDecimal.valueOf(34.3668288))
                        .country("RU")
                        .build()
        );

        when(weatherApiService.getGeoCoordinates("Bryansk", 5))
                .thenReturn(responce);

        WeatherResponceDto weather = objectMapper.readValue(jsonResponce(), WeatherResponceDto.class);

        when(weatherApiService.getWeatherByLocations(any()))
                .thenReturn(weather);
    }

    @Test
    void shouldReturnLocationsByName(){
        List<GeoResponceDto> locations = locationService.getCoordinatesByLocation("Bryansk");

        GeoResponceDto dto = locations.getFirst();
        assertThat(dto.getName()).isEqualTo("Bryansk");
        assertThat(dto.getCountry()).isEqualTo("RU");
    }

    @Test
    void shouldReturnEmptyList(){
        List<GeoResponceDto> locations = locationService.getCoordinatesByLocation("Suka");

        assertThat(locations).isEmpty();
    }

    @Test
    void shouldReturnWeather(){
        GeoResponceDto bryanskGeo = locationService.getCoordinatesByLocation("Bryansk").getFirst();
        LocationResponseDto location = LocationResponseDto.builder()
                .id(1)
                .name(bryanskGeo.getName())
                .latitude(bryanskGeo.getLatitude())
                .longitude(bryanskGeo.getLongitude())
                .build();

        List<WeatherResponceDto> weather = locationService.getWeatherByLocations(List.of(location));

        assertThat(weather).hasSize(1);
        WeatherResponceDto bryanskWeather = weather.getFirst();

        assertThat(bryanskWeather.getWeather().getTemp()).isEqualTo(-5);
        assertThat(bryanskWeather.getWeather().getFeelsLike()).isEqualTo(-7);
    }

    private static String jsonResponce(){
        return """
                {
                     "coord": {
                         "lon": 34.3668,
                         "lat": 53.2424
                     },
                     "weather": [
                         {
                             "id": 803,
                             "main": "Clouds",
                             "description": "broken clouds",
                             "icon": "04n"
                         }
                     ],
                     "base": "stations",
                     "main": {
                         "temp": -5.15,
                         "feels_like": -7.97,
                         "temp_min": -5.15,
                         "temp_max": -5.15,
                         "pressure": 1037,
                         "humidity": 88,
                         "sea_level": 1037,
                         "grnd_level": 1014
                     },
                     "visibility": 10000,
                     "wind": {
                         "speed": 1.69,
                         "deg": 62,
                         "gust": 4.07
                     },
                     "clouds": {
                         "all": 63
                     },
                     "dt": 1739201703,
                     "sys": {
                         "country": "RU",
                         "sunrise": 1739164242,
                         "sunset": 1739198578
                     },
                     "timezone": 10800,
                     "id": 576560,
                     "name": "Bryansk",
                     "cod": 200
                 }""";
    }
}