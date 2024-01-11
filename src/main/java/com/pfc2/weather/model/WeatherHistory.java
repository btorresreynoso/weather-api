package com.pfc2.weather.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class WeatherHistory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private WeatherHistoryId coord;
	private String weather;
	private Double tempMin;
	private Double tempMax;
	private Integer humity;
	private LocalDateTime created;
	
	public WeatherHistory() {
		super();
	}

	public WeatherHistoryId getCoord() {
		return coord;
	}

	public void setCoord(WeatherHistoryId coord) {
		this.coord = coord;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public Double getTempMin() {
		return tempMin;
	}

	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}

	public Double getTempMax() {
		return tempMax;
	}

	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}

	public Integer getHumity() {
		return humity;
	}

	public void setHumity(Integer humity) {
		this.humity = humity;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coord, created, humity, tempMax, tempMin, weather);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeatherHistory other = (WeatherHistory) obj;
		return Objects.equals(coord, other.coord) && Objects.equals(created, other.created)
				&& Objects.equals(humity, other.humity) && Objects.equals(tempMax, other.tempMax)
				&& Objects.equals(tempMin, other.tempMin) && Objects.equals(weather, other.weather);
	}
	
	
	
	

}
