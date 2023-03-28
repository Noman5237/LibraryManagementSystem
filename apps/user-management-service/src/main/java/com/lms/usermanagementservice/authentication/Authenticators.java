package com.lms.usermanagementservice.authentication;

import com.lms.usermanagementservice.authentication.authenticators.Authenticator;
import com.lms.usermanagementservice.authentication.authenticators.local.PasswordAuthenticator;
import com.lms.usermanagementservice.config.ApplicationContextProvider;
import com.lms.usermanagementservice.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public enum Authenticators {
	LOCAL(PasswordAuthenticator.class);
	
	private final Class<? extends Authenticator> authenticator;
	
	Authenticators(Class<? extends Authenticator> authenticator) {
		this.authenticator = authenticator;
	}
	
	public static User getAuthenticatedUser(String method, HttpServletRequest request) {
		var applicationContext = ApplicationContextProvider.getApplicationContext();
		var authenticator = applicationContext.getBean(Authenticators.valueOf(method.toUpperCase(Locale.ROOT)).authenticator);
		authenticator.setRequest(request);
		return authenticator.getAuthenticatedUser();
	}
	
}
