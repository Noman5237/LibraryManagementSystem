package com.lms.authservice.authentication.exceptions;

import com.lms.restexception.exception.RESTException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class AuthenticationException extends RESTException {
	
	{
		status = HttpStatus.BAD_REQUEST;
	}
	
	private static final String DEFAULT_MESSAGE = "Authentication failed";
	
	public AuthenticationException() {
		super(DEFAULT_MESSAGE);
	}
	
	public AuthenticationException(String message, Map<String, ?> payload) {
		super(message, payload);
	}
}
