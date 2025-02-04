package com.iposhka.controller;

import com.iposhka.dto.CreateUserDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class SignUpController {
    @GetMapping("/sign-up")
    public String signIn(@ModelAttribute("createUser")CreateUserDto userDto){
        return "/sign-up";
    }

    @PostMapping("/sign-up")
    public String checkDataAndRedirectOnSignInPage(@ModelAttribute("createUser")
                                                       @Valid CreateUserDto userDto,
                                                   BindingResult bindingResult,
                                                   Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errors", bindingResult);
            return "/sign-up";
        }

        return "redirect:/sign-in";
    }
}