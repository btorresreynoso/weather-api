package com.pfc2.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfc2.weather.model.WeatherHistory;
import com.pfc2.weather.model.WeatherHistoryId;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherHistory, WeatherHistoryId>  {

}
