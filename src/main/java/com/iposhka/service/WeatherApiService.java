package com.iposhka.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherApiService {
    @Value("${weather.api.url}")
    private static final String API_URL = "";
    @Value("${weather.api.key}")
    private static final String API_KEY = "";

    public
}