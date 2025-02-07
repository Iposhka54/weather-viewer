package com.iposhka.controller;

import com.iposhka.dto.UserLoginDto;
import com.iposhka.dto.WeatherResponceDto;
import com.iposhka.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    private final LocationService locationService;

    public HomeController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/home")
    public String home(HttpServletRequest req,
                       Model model){
        UserLoginDto user = (UserLoginDto) req.getAttribute("user");
        model.addAttribute("user", user);

        List<WeatherResponceDto> weather = locationService.getWeatherByLocations(user.getLocations());
        model.addAttribute("weathers", weather);

        return "home";
    }
}