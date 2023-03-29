package com.lms.authservice.jwt.exceptions;

import com.lms.restexception.exception.RESTException;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends RESTException {
	
	{
		status = HttpStatus.UNAUTHORIZED;
	}
	
	public InvalidRefreshTokenException() {
		super("Invalid refresh token");
	}
}
