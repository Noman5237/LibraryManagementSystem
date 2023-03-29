package com.lms.authservice.jwt;

import com.lms.authservice.dto.UserPrincipalDto;
import com.lms.authservice.jwt.exceptions.RefreshTokenAlreadyUsedException;

public interface JWTService {
	
	String generateAccessToken(String refreshToken);
	
	String generateRefreshToken(UserPrincipalDto userPrincipal);
	
	String refreshToken(String refreshToken);
	
	boolean validateAccessToken(String accessToken);
	
	boolean validateRefreshToken(String refreshToken) throws RefreshTokenAlreadyUsedException;
}
