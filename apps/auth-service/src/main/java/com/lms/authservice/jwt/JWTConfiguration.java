package com.lms.authservice.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
@RefreshScope
public class JWTConfiguration {
	
	@Value ("${security.jwt.access-token.secret}")
	private String accessTokenSecret;
	
	@Value ("${security.jwt.access-token.expiration}")
	protected int accessTokenExpiration;
	
	@Value ("${security.jwt.refresh-token.secret}")
	private String refreshTokenSecret;
	
	@Value ("${security.jwt.refresh-token.expiration}")
	protected int refreshTokenExpiration;
	
	public Key getAccessTokenKey() {
		return Keys.hmacShaKeyFor(this.accessTokenSecret.getBytes(StandardCharsets.UTF_8));
	}
	
	public Key getRefreshTokenKey() {
		return Keys.hmacShaKeyFor(this.refreshTokenSecret.getBytes(StandardCharsets.UTF_8));
	}
}
