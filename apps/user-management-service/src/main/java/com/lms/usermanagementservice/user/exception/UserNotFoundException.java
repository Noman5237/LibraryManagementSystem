package com.lms.usermanagementservice.user.exception;

import com.lms.restexception.exception.RESTException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RESTException {
	
	public UserNotFoundException(String email) {
		super("User with email " + email + " not found");
		status = HttpStatus.NOT_FOUND;
	}
}
