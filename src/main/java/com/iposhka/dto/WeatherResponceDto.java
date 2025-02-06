package com.iposhka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponceDto {
    @JsonProperty("weather")
    private WeatherInfo[] weatherInfo;

    @JsonProperty("main")
    private WeatherDigits weather;

    @JsonProperty("sys")
    private Sys sys;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherInfo{
        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;

        @JsonProperty("icon")
        private String iconCode;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherDigits{

        @JsonProperty("temp")
        private int temp;

        @JsonProperty("feels_like")
        private int feelsLike;

        @JsonProperty("humidity")
        private int humidity;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys{
        @JsonProperty("country")
        private String country;
    }
}
