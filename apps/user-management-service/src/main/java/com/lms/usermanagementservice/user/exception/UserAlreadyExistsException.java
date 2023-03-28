package com.lms.usermanagementservice.user.exception;

import com.lms.restexception.exception.RESTException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RESTException {
	
	public UserAlreadyExistsException(String email) {
		super("User with email " + email + " already exists");
		status = HttpStatus.CONFLICT;
	}
}
