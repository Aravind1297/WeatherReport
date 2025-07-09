package com.practice.weather.controller;

import com.practice.weather.service.WeatherStackService;
// import com.practice.weather.dto.WeatherDTO;
import com.practice.weather.model.WeatherStackResponse;
import com.practice.weather.dto.WeatherDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@CrossOrigin(origins = "https://weatherfrontend-465320.de.r.appspot.com/")
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherStackService weatherStackService;

    //private String city;

    // @ModelAttribute
    // public void setCity(@RequestParam String city){
    //     this.city = city.trim().toLowerCase();
    // }

   @GetMapping("/data")
   public ResponseEntity<WeatherDTO> getFullWeatherData(@RequestParam String city){
    if(city == null || city.trim().isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"City Parameter is required!");
        //return ResponseEntity.badRequest(HttpStatus.BAD_REQUEST,"City Parameter is required").build();
    }
    String sanitizedCity = city.trim().toLowerCase();
    WeatherStackResponse res = weatherStackService.getWeatherData(sanitizedCity);

    if(res == null){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();

    }
    return ResponseEntity.ok(new WeatherDTO(res));
   }

    
}
