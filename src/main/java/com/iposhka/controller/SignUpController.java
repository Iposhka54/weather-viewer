package com.iposhka.controller;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.exception.DatabaseException;
import com.iposhka.exception.UserAlreadyExistException;
import com.iposhka.service.AuthenticationService;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    private final AuthenticationService authService;

    public SignUpController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/sign-up")
    public String signIn(@ModelAttribute("createUser") CreateUserDto userDto) {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("createUser")
                         @Valid CreateUserDto userDto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "/sign-up";
        }
        try {
            authService.signIn(userDto);
            return "redirect:/sign-in";
        } catch (UserAlreadyExistException e){
            bindingResult.rejectValue("username", "error.username", e.getMessage());
            model.addAttribute("bindingResult", bindingResult);
            return "/sign-up";
        }catch (DatabaseException e){
            return "/sign-up";
        }
    }
}