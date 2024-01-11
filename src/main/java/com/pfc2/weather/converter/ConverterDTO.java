package com.pfc2.weather.converter;

import com.pfc2.weather.dto.WeatherPostResponseDTO;
import com.pfc2.weather.model.WeatherHistory;

public class ConverterDTO {
	
	/**
	 * Convierte WeatherHistory a WeatherPostDTO
	 * @param model
	 * @return
	 */
	public static WeatherPostResponseDTO convertWeatherHistoryToWeatherPostResponseDTO(WeatherHistory model) {
		return new WeatherPostResponseDTO(model.getWeather(), model.getTempMin(), model.getTempMax(), model.getHumity());
	}

}
