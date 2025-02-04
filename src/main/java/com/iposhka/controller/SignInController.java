package com.iposhka.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {
    private final Logger log;

    public SignInController(Logger log) {
        this.log = log;
    }

    @GetMapping("/sign-in")
    public String signIn(@Valid() Model model){
        log.debug("Registration page is returned");
        return "sign-in";
    }
}