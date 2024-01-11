package com.pfc2.weather.exception;

import java.io.Serializable;

public class ResponseErrorModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String[] errors;
	
	public ResponseErrorModel(String code, String[] errors) {
		super();
		this.code = code;
		this.errors = errors;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getErrors() {
		return errors;
	}
	public void setErrors(String[] errors) {
		this.errors = errors;
	}
	
}
