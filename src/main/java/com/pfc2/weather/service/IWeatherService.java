package com.pfc2.weather.service;

import java.util.List;

import javax.naming.ServiceUnavailableException;

import org.apache.coyote.BadRequestException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pfc2.weather.dto.WeatherPostResponseDTO;
import com.pfc2.weather.model.WeatherHistory;

public interface IWeatherService {
	
	/**
	 * Obtiene Informacion climatologica en base a una latitud y longitud
	 * @param lat
	 * @param lon
	 */
	public WeatherPostResponseDTO getWeatherInfo(double lat, double lon) throws JsonMappingException, JsonProcessingException, BadRequestException, ServiceUnavailableException;
	
	/**
	 * Consulta todos los registros de Informacion climatologica
	 * @return
	 */
	public List<WeatherHistory> getAllWeather();

}
