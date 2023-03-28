package com.lms.usermanagementservice.authentication.strategies;

import com.lms.usermanagementservice.user.model.User;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthenticationStrategy {
	
	@Setter
	protected HttpServletRequest request;
	
	public abstract User getAuthenticatedUser();
}
