package com.lms.usermanagementservice.exception;

import com.lms.usermanagementservice.core.exception.RESTException;

public class UserNotFoundException extends RESTException {
	
	public UserNotFoundException(String email) {
		super("User with email " + email + " not found");
	}
}
