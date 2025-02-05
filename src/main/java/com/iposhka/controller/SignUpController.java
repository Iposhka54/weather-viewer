package com.iposhka.controller;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.service.AuthenticationService;
import jakarta.validation.Valid;
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
    public String signUp(@ModelAttribute("createUser") CreateUserDto userDto) {
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
         authService.signUp(userDto);
        return "redirect:/sign-in";
    }
}