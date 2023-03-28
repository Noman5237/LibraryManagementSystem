package com.lms.usermanagementservice.authentication.authenticators.local;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.restexception.exception.RESTException;
import com.lms.usermanagementservice.authentication.authenticators.Authenticator;
import com.lms.usermanagementservice.user.exception.UserNotFoundException;
import com.lms.usermanagementservice.user.model.User;
import com.lms.usermanagementservice.user.service.UserService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PasswordAuthenticator extends Authenticator {
	
	private final UserService userService;
	
	public PasswordAuthenticator(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public User getAuthenticatedUser() {
		var passwordAuthenticationDto = parseRequest();
		var user = userService.getUser(passwordAuthenticationDto.getEmail());
		if (userService.checkHashedPassword(user.getHashedPassword(), passwordAuthenticationDto.getPassword())) {
			return user;
		}
		throw new UserNotFoundException(passwordAuthenticationDto.getEmail());
	}
	
	private PasswordAuthenticationDto parseRequest() {
		try {
			String body = request.getReader()
			                     .lines()
			                     .reduce("", (accumulator, actual) -> accumulator + actual);
			
			// use jackson databind to parse the body
			var mapper = new ObjectMapper();
			return mapper.readValue(body, PasswordAuthenticationDto.class);
		} catch (JacksonException e) {
			throw RESTException.builder()
			                   .message("Invalid parameters for request body")
			                   .build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
