package com.iposhka.controller;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.SessionDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.exception.UserAlreadyExistException;
import com.iposhka.service.AuthenticationService;
import com.iposhka.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
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

        try{
            authService.signUp(userDto);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("username", "error.username", e.getMessage());
            model.addAttribute("bindingResult", bindingResult);
            return "/sign-up";
        }

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String signIn(@ModelAttribute("userLoginDto") UserLoginDto user) {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute("userLoginDto")
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

        return "redirect:/app/home";
    }
}