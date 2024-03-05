package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {

    @Autowired
    WeatherService weatherService;

    @PostMapping
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {

        // Assuming that the request body is valid
        Weather newWeather = weatherService.createWeather(weather);

        return ResponseEntity.status(HttpStatus.CREATED).body(newWeather);
    }

    @GetMapping
    public ResponseEntity<List<Weather>> searchWeather(
        @RequestParam(required = false, name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
        @RequestParam(required = false, name = "city") List<String> cities,
        @RequestParam(required = false, name = "sort") String sort) {

        return ResponseEntity.ok(weatherService.searchWeather(date, cities, sort));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Weather> getWeather(@PathVariable("id") Integer id) {
        Weather weather = weatherService.getWeather(id);

        if (Objects.isNull(weather)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(weather);
    }

}
