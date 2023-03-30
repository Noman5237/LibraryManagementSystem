package com.lms.authservice.authentication.service;

import com.lms.authservice.jwt.JWTConfiguration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final JWTConfiguration jwtConfiguration;
	
	public AuthenticationServiceImpl(JWTConfiguration jwtConfiguration) {
		this.jwtConfiguration = jwtConfiguration;
	}
	
	@Override
	public String getRefreshTokenCookie(String refreshToken) {
		return ResponseCookie.from("refreshToken", refreshToken)
		                     .httpOnly(true)
		                     .path("/")
		                     .maxAge(Duration.of(jwtConfiguration.getRefreshTokenExpiration(), ChronoUnit.SECONDS))
		                     .sameSite("Strict")
		                     .build()
		                     .toString();
	}
}
