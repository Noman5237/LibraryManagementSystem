package com.lms.authservice.jwt;

public interface JWTService {
	
	String generateAccessToken(String refreshToken);
	
	String generateRefreshToken(String email);
	
	String refreshToken(String refreshToken);
}
