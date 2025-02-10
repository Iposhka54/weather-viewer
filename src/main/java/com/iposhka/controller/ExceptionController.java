package com.iposhka.controller;

import com.iposhka.exception.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    private static final String SORRY_MESSAGE = "Sorry for the misunderstanding. Our programmers are already trying to solve this problem!";
    @ExceptionHandler({
            CoordinatesUndefinedException.class,
            WeatherApiException.class,
            WeatherUndefinedException.class})
    public String handleWeatherApiExceptions(Model model){
        return addErrorInModelAndReturnErrorPage(model, "Problems with OpenWeather API server." + SORRY_MESSAGE);
    }

    @ExceptionHandler(DatabaseException.class)
    public String handleDatabaseException(Exception e, Model model){
        return addErrorInModelAndReturnErrorPage(model, e.getMessage() + "\n" + SORRY_MESSAGE);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handlePasswordCredentialsException(Exception e, Model model){
        return addErrorInModelAndReturnErrorPage(model, "Invalid credentials!" + SORRY_MESSAGE);
    }

    @ExceptionHandler({
            EntityAlreadyExistException.class,
            UserNotFoundException.class
    })
    public String handleUsersException(Model model, Exception e){
        return addErrorInModelAndReturnErrorPage(model, e.getMessage() + "\n" + SORRY_MESSAGE);
    }

    @ExceptionHandler({
            Exception.class
    })
    public String handleException(Model model, Exception e){
        return addErrorInModelAndReturnErrorPage(model, "Sorry for the misunderstanding. Our programmers are already trying to solve this problem");
    }

    private String addErrorInModelAndReturnErrorPage(Model model, String message){
        model.addAttribute("error", message);
        return "error";
    }
}
