package com.iposhka.controller;

import com.iposhka.exception.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({
            CoordinatesUndefinedException.class,
            WeatherApiException.class,
            WeatherUndefinedException.class})
    public String handleWeatherApiExceptions(Model model){
        return addErrorInModelAndReturnErrorPage(model, "Problems with OpenWeather API server");
    }

    @ExceptionHandler(DatabaseException.class)
    public String handleDatabaseException(Exception e, Model model){
        return addErrorInModelAndReturnErrorPage(model, e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handlePasswordCredentialsException(Exception e, Model model){
        return addErrorInModelAndReturnErrorPage(model, "Invalid credentials!");
    }

    @ExceptionHandler({
            EntityAlreadyExistException.class,
            UserNotFoundException.class
    })
    public String handleUsersException(Model model, Exception e){
        return addErrorInModelAndReturnErrorPage(model, e.getMessage());
    }

    private String addErrorInModelAndReturnErrorPage(Model model, String message){
        model.addAttribute("error", message);
        return "error";
    }
}
