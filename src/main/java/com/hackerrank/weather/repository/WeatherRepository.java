package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    @Query(value = "SELECT w FROM Weather w WHERE " +
        "(:date IS NULL OR w.date = :date) AND " +
        "(:city IS NULL OR UPPER(w.city) IN (:city)) " +
        "ORDER BY w.id ASC")
    public List<Weather> searchOrderByIdAsc(@Param("date") Date date, @Param("city") List<String> city);

    @Query(value = "SELECT w FROM Weather w WHERE " +
        "(:date IS NULL OR w.date = :date) AND " +
        "(:city IS NULL OR UPPER(w.city) IN (:city)) " +
        "ORDER BY w.date ASC")
    public List<Weather> searchOrderByDateAsc(@Param("date") Date date, @Param("city") List<String> city);

    @Query(value = "SELECT w FROM Weather w WHERE " +
        "(:date IS NULL OR w.date = :date) AND " +
        "(:city IS NULL OR UPPER(w.city) IN (:city)) " +
        "ORDER BY w.date DESC")
    public List<Weather> searchOrderByDateDesc(@Param("date") Date date, @Param("city") List<String> city);

}
