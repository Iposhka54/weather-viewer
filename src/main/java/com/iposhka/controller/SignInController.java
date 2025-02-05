package com.iposhka.controller;

import com.iposhka.dto.SessionDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.service.AuthenticationService;
import com.iposhka.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public String signIn(@ModelAttribute("userDto") UserLoginDto user) {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute("userDto")
                         @Valid UserLoginDto userLoginDto,
                         BindingResult bindingResult,
                         Model model,
                         HttpServletResponse res){
        if(bindingResult.hasErrors()){
            model.addAttribute("bindingResult", bindingResult);
            return "/sign-in";
        }

        UserLoginDto user = authService.login(userLoginDto);

        SessionDto sessionDto = authService.openSession(user);

        Cookie cookie = new Cookie("sessionId", sessionDto.getId().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(SessionService.getSessionTimeout());

        res.addCookie(cookie);

        return "/home";
    }
}