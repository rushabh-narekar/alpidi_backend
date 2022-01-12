package com.alpidi.model;

import org.springframework.lang.Nullable;

public class ApiResponse {
	private Integer code;
	private String message;
	@Nullable
	private String result;
	
	public ApiResponse(Integer code, String message,String result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}
	
	public Integer getCode() {
		return this.code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return this.result;
	}
	public void setResult(@Nullable String result) {
		this.result = result;
	}
}
