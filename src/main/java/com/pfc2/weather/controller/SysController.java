package com.pfc2.weather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/sys")
@Tag(name = "Sys", description = "Operaciones de Sistema")
public class SysController {

	@GetMapping("/status")
	@Operation(operationId = "getStatus", summary = "Obtener Status", description = "Obtiene un Status 200.")
	@ApiResponse(responseCode = "200", description = "Status 200")
	public ResponseEntity<?> getStatus() {
		return ResponseEntity.ok().body("");
	}
}
