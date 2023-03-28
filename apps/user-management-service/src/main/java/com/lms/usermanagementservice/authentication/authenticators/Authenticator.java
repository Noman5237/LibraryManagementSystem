package com.lms.usermanagementservice.authentication.authenticators;

import com.lms.usermanagementservice.user.model.User;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

public abstract class Authenticator {
	
	@Setter
	protected HttpServletRequest request;
	
	public abstract User getAuthenticatedUser();
}
