package com.pfc2.weather.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.ServiceUnavailableException;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfc2.weather.converter.ConverterDTO;
import com.pfc2.weather.dto.WeatherPostResponseDTO;
import com.pfc2.weather.model.WeatherHistory;
import com.pfc2.weather.model.WeatherHistoryId;
import com.pfc2.weather.repository.WeatherRepository;
import com.pfc2.weather.service.IWeatherService;

@Service
public class WeatherServiceImpl implements IWeatherService {
	
	@Value("${openweather.api-key}")
	private String apiKey;
	
	@Value("${openweather.api-url}")
	private String apiUrl;
	
	@Autowired
	private WeatherRepository weatherRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public WeatherPostResponseDTO getWeatherInfo(double lat, double lon) throws JsonMappingException, JsonProcessingException, BadRequestException, ServiceUnavailableException {
		WeatherPostResponseDTO weather = null;
		boolean consultarApi = true;
		// Consulta registro
		Optional<WeatherHistory> weatherHistory = weatherRepository.findById(new WeatherHistoryId(lat, lon));
		if (weatherHistory.isPresent()) {
			// Si existe el registro, valida si la diferencia entre la fecha de creacion y la fecha actual es menor a 10 minutos
			Duration duracion = Duration.between(weatherHistory.get().getCreated(), LocalDateTime.now());
	        long minutos = duracion.toMinutes();
	        if (minutos < 10) {
	        	weather = ConverterDTO.convertWeatherHistoryToWeatherPostResponseDTO(weatherHistory.get());
	        	consultarApi = false;
	        }
		}
		// Valida si debe consultar API de Tercero
		if (consultarApi) {
			// Consulta api de tercero
        	Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("lat", lat);
            queryParams.put("lon", lon);
            queryParams.put("appid", apiKey);
           
            // Se establece un Timeout de 15 segundos
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(15000);
            requestFactory.setReadTimeout(15000);
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            // Prepara URL
            String urlWithParams = apiUrl + "?lat={lat}&lon={lon}&appid={appid}";
            try {
            	// Realiza el Request
                ResponseEntity<String> response = restTemplate.getForEntity(urlWithParams, String.class, queryParams);
                // Valida Response
                if (response.getStatusCode().is2xxSuccessful()) {
                	String responseBody = response.getBody();
                	ObjectMapper objectMapper = new ObjectMapper();
                	JsonNode root = objectMapper.readTree(responseBody);
                	// Prepara un nuevo objeto
                	WeatherHistory weatherHistoryNew = new WeatherHistory();
                	weatherHistoryNew.setCoord(new WeatherHistoryId(lat, lon));
                	weatherHistoryNew.setWeather(root.path("weather").get(0).path("main").asText());
                	weatherHistoryNew.setTempMax(root.path("main").path("temp_max").asDouble());
                	weatherHistoryNew.setTempMin(root.path("main").path("temp_min").asDouble());
                	weatherHistoryNew.setHumity(root.path("main").path("humidity").asInt());
                	weatherHistoryNew.setCreated(LocalDateTime.now());
                	// Persiste el objeto
                	weatherRepository.save(weatherHistoryNew);
                	// Convierte a DTO
                	weather = ConverterDTO.convertWeatherHistoryToWeatherPostResponseDTO(weatherHistoryNew);
                }
			} catch (HttpClientErrorException e) {
					if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST) || e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
						String responseBody = e.getResponseBodyAsString();
	                	ObjectMapper objectMapper = new ObjectMapper();
	                	JsonNode root = objectMapper.readTree(responseBody);
	                	throw new HttpClientErrorException(e.getStatusCode(), root.path("message").asText());
					} else {
						throw new ServiceUnavailableException(e.getMessage());
					}
					
			} catch (Exception e) {
				throw new ServiceUnavailableException(e.getMessage());
			}
		}
		return weather;
	}
	
	@Override
	public List<WeatherHistory> getAllWeather() {
		return weatherRepository.findAll();
	}
	

}
