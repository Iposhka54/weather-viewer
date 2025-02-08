package com.iposhka.controller;

import com.iposhka.dto.LocationResponseDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @PostMapping("/locations/add")
    public String addLocation(@RequestParam("lat") BigDecimal latitude,
                              @RequestParam("lon") BigDecimal longitude,
                              @RequestParam("name") String name,
                              HttpServletRequest req){
        UserLoginDto user = (UserLoginDto) req.getAttribute("user");
        locationService.addLocation(latitude, longitude, name, user);
        return "redirect:/home";
    }

    @DeleteMapping("/locations/{id}")
    public String deleteLocation(@PathVariable("id") int locationId,
                                 HttpServletRequest req){
        int userId = (int) req.getAttribute("userId");

        locationService.deleteLocation(locationId, userId);
        return "redirect:/home";
    }
}
