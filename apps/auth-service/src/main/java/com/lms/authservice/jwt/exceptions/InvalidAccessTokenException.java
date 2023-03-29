package com.lms.authservice.jwt.exceptions;

import com.lms.restexception.exception.RESTException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends RESTException {
	
	{
		status = HttpStatus.UNAUTHORIZED;
	}
	
	public InvalidAccessTokenException() {
		super("Invalid access token");
	}
	
	public InvalidAccessTokenException(String message) {
		super(message);
	}
	
}
