package com.lms.usermanagementservice.authentication;

import com.lms.usermanagementservice.authentication.strategies.AuthenticationStrategy;
import com.lms.usermanagementservice.authentication.strategies.local.PasswordAuthenticationStrategy;
import com.lms.usermanagementservice.config.ApplicationContextProvider;
import com.lms.usermanagementservice.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public enum AuthenticationStrategies {
	LOCAL(PasswordAuthenticationStrategy.class);
	
	private final Class<? extends AuthenticationStrategy> authenticationStrategist;
	
	AuthenticationStrategies(Class<? extends AuthenticationStrategy> authenticationStrategist) {
		this.authenticationStrategist = authenticationStrategist;
	}
	
	public static User getAuthenticatedUser(String method, HttpServletRequest request) {
		var applicationContext = ApplicationContextProvider.getApplicationContext();
		var strategist = applicationContext.getBean(AuthenticationStrategies.valueOf(method.toUpperCase(Locale.ROOT)).authenticationStrategist);
		strategist.setRequest(request);
		return strategist.getAuthenticatedUser();
	}
	
}
