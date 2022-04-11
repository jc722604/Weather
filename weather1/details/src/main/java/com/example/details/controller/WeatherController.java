package com.example.details.controller;

import com.example.details.exception.NotFoundException;
import com.example.details.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WeatherController {

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    @Value("${server.port}")
    private int randomServerPort;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/details")
    public ResponseEntity<?> queryWeatherByCity(@RequestParam(required = true) String city) {
        logger.trace("Access queryWeatherByCity");
        if(city == null) {
            throw new NotFoundException("'CITY IS EMPTY");
        }
        return new ResponseEntity<>(weatherService.findCityIdByName(city), HttpStatus.OK);
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<?> queryWeatherByCity(@PathVariable int id) {
        logger.trace("Access queryWeatherByCity");
        return new ResponseEntity<Map>(weatherService.findCityNameById(id), HttpStatus.OK);
    }

    @GetMapping("/details/port")
    public ResponseEntity<?> queryWeatherByCity() {
        logger.trace("Access queryWeatherByCity");
        return new ResponseEntity<>("weather service + " + randomServerPort, HttpStatus.OK);
    }
}
