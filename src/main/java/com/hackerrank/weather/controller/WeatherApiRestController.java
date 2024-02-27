package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
public class WeatherApiRestController {

    @Autowired
    WeatherRepository weatherRepository;

    @PostMapping(path = "/weather")
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {

        // Assuming that the request body is valid
        Weather newWeather = weatherRepository.save(weather);

        return ResponseEntity.status(HttpStatus.CREATED).body(newWeather);
    }

    @GetMapping(path = "/weather")
    public ResponseEntity<List<Weather>> searchWeather(
        @RequestParam(required = false, name = "date") Date date,
        @RequestParam(required = false, name = "city") List<String> city,
        @RequestParam(required = false, name = "sort") String sort) {

        List<String> cityUpperCase = city != null ? city.stream().map(String::toUpperCase).toList() : null;

        String orderBy = "ORDER BY w.id ASC";

        if (!Objects.isNull(sort)) {
            if (sort.equals("date")) {
                return ResponseEntity.ok(weatherRepository.searchOrderByDateAsc(date, cityUpperCase));
            }

            if (sort.equals("-date")) {
                return ResponseEntity.ok(weatherRepository.searchOrderByDateDesc(date, cityUpperCase));
            }
        }

        return ResponseEntity.ok(weatherRepository.searchOrderByIdAsc(date, cityUpperCase));
    }

    @GetMapping(path = "/weather/{id}")
    public ResponseEntity<Weather> getWeather(@PathVariable("id") Integer id) {
        Weather weather = weatherRepository.findById(id).orElse(null);

        if (Objects.isNull(weather)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(weather);
    }

}
