package com.lms.usermanagementservice.exception;

import com.lms.restexception.exception.RESTException;

public class UserAlreadyExistsException extends RESTException {
	
	public UserAlreadyExistsException(String email) {
		super("User with email " + email + " already exists");
	}
}
