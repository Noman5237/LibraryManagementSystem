package com.lms.authservice.dto;

public class TokenPairDto {
	
	public final String refreshToken;
	public final String accessToken;
	
	public TokenPairDto(String refreshToken, String accessToken) {
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
	}
}
