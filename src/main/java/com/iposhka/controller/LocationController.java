package com.iposhka.controller;

import com.iposhka.dto.UserLoginDto;
import com.iposhka.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("location") String location,
                         Model model, HttpServletRequest req){
        if(location.isBlank()){
            model.addAttribute("error", "Location parameter cannot be empty");
            return "error";
        }
        UserLoginDto user = (UserLoginDto) req.getAttribute("user");
        model.addAttribute("user", user);

        var locations = locationService.getCoordinatesByLocation(location);

        model.addAttribute("locations", locations);
        model.addAttribute("nameLocation", location);
        return "search-results";
    }
}
