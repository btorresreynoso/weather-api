package com.pfc2.weather.dto;

import java.io.Serializable;

public class WeatherPostResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String weather;
	private Double tempMin;
	private Double tempMax;
	private Integer humity;
	
	public WeatherPostResponseDTO() {
		super();
	}

	public WeatherPostResponseDTO(String weather, Double tempMin, Double tempMax, Integer humity) {
		super();
		this.weather = weather;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.humity = humity;
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

	
}
