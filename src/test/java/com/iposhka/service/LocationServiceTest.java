package com.iposhka.service;

import com.iposhka.config.TestConfig;
import com.iposhka.dto.GeoResponceDto;
import com.iposhka.dto.LocationResponseDto;
import com.iposhka.dto.WeatherResponceDto;
import com.iposhka.mapper.LocationMapper;
import com.iposhka.mapper.UserMapper;
import com.iposhka.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
@SpringJUnitConfig(classes = TestConfig.class)
class LocationServiceTest {

    @Mock WeatherApiService weatherApiService;

    @InjectMocks
    private LocationService locationService;

    @Test
    void ShouldListWhenGetCoordinatesByLocation(){
        when(weatherApiService.getGeoCoordinates("Moscow", 5))
                .thenReturn(List.of(
                        GeoResponceDto.builder()
                                .name("Moscow")
                                .country("RU")
                                .latitude(BigDecimal.ONE)
                                .longitude(BigDecimal.ONE)
                        .build()));

        List<GeoResponceDto> actualResult = locationService.getCoordinatesByLocation("Moscow");

        assertThat(actualResult).hasSize(1);
        assertThat(actualResult.getFirst().getName()).isEqualTo("Moscow");
    }

    @Test
    void ShouldEmptyListWhenGetCoordinatesByEmptyString(){
        List<GeoResponceDto> actualResult = locationService.getCoordinatesByLocation("");

        assertThat(actualResult).hasSize(0);
    }

    @Test
    void getWeatherByLocations_shouldReturnWeatherData() {
        LocationResponseDto location = LocationResponseDto.builder()
                .id(1)
                .name("Moscow")
                .latitude(BigDecimal.ONE)
                .longitude(BigDecimal.ONE)
                .build();

        WeatherResponceDto mockWeather = new WeatherResponceDto();
        mockWeather.setName("Moscow");

        when(weatherApiService.getWeatherByLocations(location)).thenReturn(mockWeather);

        List<WeatherResponceDto> result = locationService.getWeatherByLocations(List.of(location));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Moscow");
    }
}