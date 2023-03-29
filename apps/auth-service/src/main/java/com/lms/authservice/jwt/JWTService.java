package com.lms.authservice.jwt;

import com.lms.authservice.authentication.dto.UserPrincipalDto;

public interface JWTService {
	
	String generateAccessToken(String refreshToken);
	
	String generateRefreshToken(UserPrincipalDto userPrincipal);
	
	String refreshToken(String refreshToken);
	
	boolean validateAccessToken(String accessToken);
}
