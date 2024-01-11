package com.pfc2.weather.exception;

import javax.naming.ServiceUnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String[] errors = new String[ex.getBindingResult().getAllErrors().size()];
		FieldError error;
		for (int i = 0; i < ex.getBindingResult().getAllErrors().size(); i++) {
			error = (FieldError) ex.getBindingResult().getAllErrors().get(i);
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			errors[i] = fieldName + ": " + errorMessage;
		}
		ResponseErrorModel apiError = new ResponseErrorModel(HttpStatus.BAD_REQUEST.toString(), errors);
		logger.error("", ex);
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ResponseErrorModel apiError = new ResponseErrorModel(HttpStatus.BAD_REQUEST.toString(),
				new String[] {ex.getMessage()});
		logger.error("", ex);
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ResponseErrorModel> handleException(HttpClientErrorException ex) {
		ResponseErrorModel apiError = new ResponseErrorModel(ex.getStatusCode().toString(),
				new String[] {ex.getMessage()});
		logger.error("", ex);
		return ResponseEntity.status(ex.getStatusCode()).body(apiError);
	}
	
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<ResponseErrorModel> handleException(ServiceUnavailableException ex) {
		ResponseErrorModel apiError = new ResponseErrorModel(HttpStatus.SERVICE_UNAVAILABLE.toString(),
				new String[] {ex.getMessage()});
		logger.error("", ex);
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseErrorModel> handleException(Exception ex) {
		ResponseErrorModel apiError = new ResponseErrorModel(HttpStatus.SERVICE_UNAVAILABLE.toString(),
				new String[] {ex.getMessage()});
		logger.error("", ex);
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseErrorModel> handleRuntimeException(RuntimeException ex) {
		ResponseErrorModel apiError = new ResponseErrorModel(HttpStatus.SERVICE_UNAVAILABLE.toString(),
				new String[] {ex.getMessage()});
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
	}
}
