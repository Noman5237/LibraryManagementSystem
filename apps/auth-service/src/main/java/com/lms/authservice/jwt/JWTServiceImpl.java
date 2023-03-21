package com.lms.authservice.jwt;

import org.springframework.stereotype.Service;

@Service
public class JWTServiceImpl implements JWTService {
	
	private final JWTConfiguration jwtConfiguration;
	
	public JWTServiceImpl(JWTConfiguration jwtConfiguration) {
		this.jwtConfiguration = jwtConfiguration;
		System.out.println(jwtConfiguration.secret);
	}
	
	@Override
	public String generateAccessToken(String email) {
		return null;
	}
	
	@Override
	public String generateRefreshToken(String email) {
		return null;
	}
	
	@Override
	public boolean validateRefreshToken(String token) {
		return token.equals("valid");
	}
}
