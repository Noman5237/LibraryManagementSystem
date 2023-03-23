package com.lms.authservice.jwt.tokens.refreshtoken;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Getter
@Setter
@Builder
@RedisHash ("refresh-tokens")
public class RefreshToken implements Serializable {
	
	@Id
	private String token;
	
	@TimeToLive
	private Long expiration;
	
	private String successor;
	
	public boolean isUsed() {
		return successor != null;
	}
}
