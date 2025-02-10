package com.iposhka.controller;

import com.iposhka.dto.CreateUserDto;
import com.iposhka.dto.SessionDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.exception.InvalidCredentialsException;
import com.iposhka.exception.EntityAlreadyExistException;
import com.iposhka.exception.UserNotFoundException;
import com.iposhka.service.AuthenticationService;
import com.iposhka.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authService;
    private final Logger log;

    public AuthController(AuthenticationService authService, Logger log) {
        this.authService = authService;
        this.log = log;
    }

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("createUser") CreateUserDto userDto,
                         BindingResult bindingResult,
                         Model model){
        model.addAttribute("bindingResult", bindingResult);
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpPost(@ModelAttribute("createUser")
                         @Valid CreateUserDto userDto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "/sign-up";
        }

        try{
            authService.signUp(userDto);
        }catch (EntityAlreadyExistException e){
            bindingResult.rejectValue("username", "error.username", "User already exist");
            model.addAttribute("bindingResult", bindingResult);
            return "/sign-up";
        }

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String signIn(@ModelAttribute("userLoginDto") UserLoginDto user,
                         BindingResult bindingResult,
                         Model model){
        model.addAttribute("bindingResult", bindingResult);
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

        try {
            UserLoginDto user = authService.login(userLoginDto);
            openSessionAndSetCookie(user, res);
        } catch (InvalidCredentialsException e) {
            return handleException(bindingResult, model, "password", e.getMessage(), "sign-in");
        } catch (UserNotFoundException e) {
            return handleException(bindingResult, model, "username", e.getMessage(), "sign-in");
        }

        return "redirect:/home";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest req, HttpServletResponse res){
        Cookie cookie = WebUtils.getCookie(req, "sessionId");
        if (cookie != null){
            try{
                UUID uuid = UUID.fromString(cookie.getValue());

                clearCookie(cookie, res);

                authService.deleteSessionByUUID(uuid);
            }catch (Exception e){
                log.error("Cookie or session not found");
            }
        }

        return "redirect:/auth/sign-in";
    }

    private void openSessionAndSetCookie(UserLoginDto user, HttpServletResponse res) {
        SessionDto sessionDto = authService.openSession(user);
        Cookie cookie = new Cookie("sessionId", sessionDto.getId().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(SessionService.getSessionTimeout());
        res.addCookie(cookie);
    }

    private void clearCookie(Cookie cookie, HttpServletResponse res) {
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    private String handleBindingErrors(Model model, BindingResult bindingResult, String viewName) {
        model.addAttribute("bindingResult", bindingResult);
        return viewName;
    }

    private String handleException(BindingResult bindingResult, Model model, String field, String message, String viewName) {
        bindingResult.rejectValue(field, "error." + field, message);
        return handleBindingErrors(model, bindingResult, viewName);
    }
}