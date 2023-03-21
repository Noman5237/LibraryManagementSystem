package com.lms.authservice.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties (prefix = "security.jwt")
public class JWTConfiguration {
	
	protected String accessTokenSecret;
	protected int accessTokenExpiration;
	protected String refreshTokenSecret;
	protected int refreshTokenExpiration;
}
