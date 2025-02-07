package com.iposhka.controller;

import com.iposhka.dto.SessionDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.service.WeatherApiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
    private final WeatherApiService weatherApiService;

    public HomeController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @GetMapping("/home")
    public String home(HttpServletRequest req,
                       Model model){
        UserLoginDto user = (UserLoginDto) req.getAttribute("user");
        model.addAttribute("user", user);

        return "home";
    }

    @GetMapping("/search")
    public String search(){
        return "search-results";
    }
}