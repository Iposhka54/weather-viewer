package com.iposhka.exception;

public class WeatherUndefinedException extends RuntimeException {
    public WeatherUndefinedException(String message) {
        super(message);
    }
}
