package com.lms.usermanagementservice.exception;

import com.lms.restexception.exception.RESTException;

public class UserNotFoundException extends RESTException {
	
	public UserNotFoundException(String email) {
		super("User with email " + email + " not found");
	}
}
