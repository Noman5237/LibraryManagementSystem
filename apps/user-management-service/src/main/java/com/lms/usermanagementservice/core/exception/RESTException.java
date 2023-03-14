package com.lms.usermanagementservice.core.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class RESTException extends RuntimeException {
	
	private final Map<String, Object> payload;
	
	public RESTException(String message) {
		super(message);
		this.payload = null;
	}
	
	public RESTException(String message, Map<String, Object> payload) {
		super(message);
		this.payload = payload;
	}
}
