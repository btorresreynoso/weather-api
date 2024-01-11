package com.pfc2.weather.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pfc2.weather.dto.WeatherPostRequestDTO;
import com.pfc2.weather.dto.WeatherPostResponseDTO;
import com.pfc2.weather.exception.ResponseErrorModel;
import com.pfc2.weather.model.WeatherHistory;
import com.pfc2.weather.service.IWeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/weather")
@Tag(name = "Weather", description = "Operaciones de Clima")
@ApiResponses(value = { 
		@ApiResponse(responseCode = "400", description = "Parametros invalidos", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorModel.class)) }),
		@ApiResponse(responseCode = "404", description = "Datos no encontrados", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorModel.class)) }),
		@ApiResponse(responseCode = "401", description = "Acceso no Autorizado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorModel.class)) }),
		@ApiResponse(responseCode = "503", description = "Error no controlado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorModel.class)) })
})
public class WeatherController {
	
	@Autowired
	private IWeatherService weatherService;
	
	@PostMapping()
	@Operation(operationId = "postWeather", summary = "Obtener Clima", description = "Obtiene informacion climatologica de un punto geografico.")
	@ApiResponse(responseCode = "200", description = "Informacion Climatologica encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherPostResponseDTO.class)) })
	public ResponseEntity<?> postWeather(@Valid @NotNull @RequestBody WeatherPostRequestDTO requestBody) throws JsonMappingException, JsonProcessingException {
		WeatherPostResponseDTO response = weatherService.getWeatherInfo(requestBody.getLat(), requestBody.getLon());
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/history")
	@Operation(operationId = "getHistory", summary = "Historial de consultas de Clima", description = "Obtener Historial de consultas de Clima realizadas.")
	@ApiResponse(responseCode = "200", description = "Historial", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherHistory.class)) })
	public ResponseEntity<?> getWeather() {
		List<WeatherHistory> lsHistory = weatherService.getAllWeather();
		if (ObjectUtils.isEmpty(lsHistory))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseErrorModel(HttpStatus.NOT_FOUND.toString(), new String[] {"No se han encontrado datos."}));
		else
			return ResponseEntity.ok().body(lsHistory);
	}

}
