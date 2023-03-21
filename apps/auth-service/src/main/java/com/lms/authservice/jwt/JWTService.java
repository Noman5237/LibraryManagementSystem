package com.lms.authservice.jwt;

public interface JWTService {
	
	String generateAccessToken(String email);
	
	String generateRefreshToken(String email);
	
	boolean validateRefreshToken(String token);
}
