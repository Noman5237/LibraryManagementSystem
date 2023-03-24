package com.lms.restexception.exception;

import lombok.Getter;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Getter
public abstract class RESTException extends RestClientException {
	
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
