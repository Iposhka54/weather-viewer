package com.iposhka.controller;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.exception.DatabaseException;
import com.iposhka.exception.UserAlreadyExistException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUserAlreadyExist(UserAlreadyExistException e,
                                         @ModelAttribute("createUser") CreateUserDto userDto,
                                         BindingResult bindingResult,
                                         Model model) {
        bindingResult.rejectValue("username", "error.username", e.getMessage());
        model.addAttribute("bindingResult", bindingResult);
        return "/sign-up";
    }

    @ExceptionHandler(DatabaseException.class)
    public String handleDatabaseErrors(DatabaseException e,
                                      BindingResult bindingResult,
                                      Model model) {
        bindingResult.rejectValue("error", "error.error", e.getMessage());
        model.addAttribute("bindingResult", bindingResult);
        return "error";
    }
}
