package com.pfc2.weather.dto;

import jakarta.validation.constraints.NotNull;

public class WeatherPostRequestDTO {
	
	@NotNull
	private Double lat;
	
	@NotNull
	private Double lon;

	public WeatherPostRequestDTO() {
		super();
	}

	public WeatherPostRequestDTO(@NotNull Double lat, @NotNull Double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	

}
