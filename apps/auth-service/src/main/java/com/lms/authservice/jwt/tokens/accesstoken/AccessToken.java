package com.lms.authservice.jwt.tokens.accesstoken;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class AccessToken implements Serializable {
	
	private String token;
	
	private Long expiration;
	
}
