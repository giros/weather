package com.hackerrank.weather.service;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherService {

    @Autowired
    WeatherRepository weatherRepository;

    public Weather createWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    public List<Weather> searchWeather(Date date, List<String> cities, String sort) {
        List<String> cityUpperCase = cities != null ? cities.stream().map(String::toUpperCase).toList() : null;

        Specification<Weather> specification = Specification.where(isDateEquals(date).or(isCityIn(cityUpperCase)));

        return weatherRepository.findAll(specification, orderBy(sort));
    }

    public Weather getWeather(Integer id) {
        return weatherRepository.findById(id).orElse(null);
    }

    private Specification<Weather> isDateEquals(Date date) {
        if (Objects.isNull(date)) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date);
    }

    private Specification<Weather> isCityIn(List<String> cities) {
        if (Objects.isNull(cities)) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.upper(root.get("city")).in(cities);
    }

    private Sort orderBy(String sort) {
        if (!Objects.isNull(sort)) {
            if (sort.equals("date")) {
                return Sort.by(Sort.Direction.ASC, "date");
            }

            if (sort.equals("-date")) {
                return Sort.by(Sort.Direction.DESC, "date");
            }
        }

        return Sort.by(Sort.Direction.ASC, "id");
    }

}
