package com.iposhka.controller;

import com.iposhka.dto.UserDto;
import com.iposhka.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignInController {
    private final AuthenticationService authService;

    public SignInController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/sign-in")
    public String signIn(@ModelAttribute("userDto") UserDto user) {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute("userDto")
                         @Valid UserDto userDto,
                         BindingResult bindingResult,
                         Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("bindingResult", bindingResult);
            return "/sign-in";
        }

        UserDto user = authService.login(userDto);



        return "/home";
    }
}