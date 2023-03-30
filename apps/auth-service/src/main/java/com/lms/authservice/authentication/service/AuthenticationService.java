package com.lms.authservice.authentication.service;

public interface AuthenticationService {
	
	String getRefreshTokenCookie(String refreshToken);
}
