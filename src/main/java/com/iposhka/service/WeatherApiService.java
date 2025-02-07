package com.iposhka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iposhka.dto.GeoResponceDto;
import com.iposhka.dto.LocationResponseDto;
import com.iposhka.dto.WeatherResponceDto;
import com.iposhka.exception.CoordinatesUndefinedException;
import com.iposhka.exception.WeatherApiException;
import com.iposhka.exception.WeatherUndefinedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WeatherApiService {
    private final String apiUrl = "http://api.openweathermap.org";

    @Value("${weather.api.key}")
    private String apiKey;
    private static final String units = "metric";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;


    public WeatherApiService(WebClient.Builder webClient, ObjectMapper objectMapper) {
        this.webClient = webClient
                .baseUrl(apiUrl)
                .build();
        this.objectMapper = objectMapper;
    }

    public List<GeoResponceDto> getGeoCoordinates(String location, int limit){
        return webClient.get()
                .uri(uri -> uri.path("/geo/1.0/direct")
                        .queryParam("q", location)
                        .queryParam("limit", limit)
                        .queryParam("appid", apiKey)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new CoordinatesUndefinedException("Coordinates is undefined")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new WeatherApiException("Weather server not working")))
                .bodyToMono(new ParameterizedTypeReference<List<GeoResponceDto>>() {
                })
                .block();
    }

    public WeatherResponceDto getWeatherByLocations(LocationResponseDto location){
        return webClient.get()
                .uri(uri -> uri.path("/data/2.5/weather")
                        .queryParam("lat", location.getLatitude())
                        .queryParam("lon", location.getLongitude())
                        .queryParam("appid", apiKey)
                        .queryParam("units", units)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new WeatherUndefinedException("Weather is undefined")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new WeatherApiException("Weather server not working")))
                .bodyToMono(new ParameterizedTypeReference<WeatherResponceDto>() {
                })
                .block();
    }
}