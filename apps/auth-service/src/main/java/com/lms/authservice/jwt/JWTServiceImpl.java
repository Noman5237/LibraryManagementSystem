package com.lms.authservice.jwt;

import com.lms.authservice.dto.UserPrincipalDto;
import com.lms.authservice.jwt.exceptions.InvalidAccessTokenException;
import com.lms.authservice.jwt.exceptions.InvalidRefreshTokenException;
import com.lms.authservice.jwt.exceptions.RefreshTokenAlreadyUsedException;
import com.lms.authservice.jwt.tokens.accesstoken.AccessToken;
import com.lms.authservice.jwt.tokens.refreshtoken.RefreshToken;
import com.lms.authservice.jwt.tokens.refreshtoken.RefreshTokenRepository;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JWTServiceImpl implements JWTService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	private final JWTConfiguration jwtConfiguration;
	
	public JWTServiceImpl(RefreshTokenRepository refreshTokenRepository, JWTConfiguration jwtConfiguration) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtConfiguration = jwtConfiguration;
	}
	
	@Override
	public String generateAccessToken(String refreshToken) {
		boolean isValid = false;
		try {
			isValid = validateRefreshToken(refreshToken);
		} catch (RefreshTokenAlreadyUsedException ignored) {
			invalidateRefreshTokenChain(refreshToken);
		}
		
		if (!isValid) {
			throw new InvalidRefreshTokenException();
		}
		
		var accessToken = createAccessToken(refreshToken);
		return accessToken.getToken();
	}
	
	@Override
	public String generateRefreshToken(UserPrincipalDto userPrincipal) {
		var refreshToken = createRefreshToken(userPrincipal);
		refreshTokenRepository.save(refreshToken);
		return refreshToken.getToken();
	}
	
	@Override
	public String refreshToken(String refreshToken) {
		boolean isVerified = false;
		try {
			isVerified = validateRefreshToken(refreshToken);
		} catch (RefreshTokenAlreadyUsedException ignored) {
			invalidateRefreshTokenChain(refreshToken);
		}
		
		if (!isVerified) {
			throw new InvalidRefreshTokenException();
		}
		
		var newRefreshToken = createRefreshToken(refreshToken);
		refreshTokenRepository.save(newRefreshToken);
		return newRefreshToken.getToken();
	}
	
	public boolean validateAccessToken(String token) {
		try {
			parseJWT(token, jwtConfiguration.getAccessTokenKey());
			return true;
		} catch (JwtException ignored) {
			return false;
		}
	}
	
	public boolean validateRefreshToken(String token) throws RefreshTokenAlreadyUsedException {
		try {
			Jwts.parserBuilder()
			    .setSigningKey(jwtConfiguration.getRefreshTokenKey())
			    .build()
			    .parseClaimsJws(token);
			
			var refreshToken = refreshTokenRepository.findById(token);
			if (refreshToken.isEmpty()) {
				return false;
			}
			
			RefreshToken storedToken = refreshToken.get();
			boolean used = storedToken.isUsed();
			if (used) {
				throw new RefreshTokenAlreadyUsedException();
			}
			
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
	
	private Jws<Claims> parseJWT(String token, Key signingKey) {
		try {
			return Jwts.parserBuilder()
			           .setSigningKey(signingKey)
			           .build()
			           .parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new InvalidAccessTokenException("Access token expired");
		} catch (JwtException e) {
			throw new InvalidAccessTokenException();
		}
	}
	
	private RefreshToken createRefreshToken(UserPrincipalDto userPrincipal) {
		
		var tokenString = Jwts.builder()
		                      .signWith(jwtConfiguration.getRefreshTokenKey())
		                      .setSubject(userPrincipal.getEmail())
		                      .claim("role",
		                             userPrincipal.getUserRole()
		                                          .name())
		                      .setIssuedAt(new java.util.Date())
		                      .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtConfiguration.refreshTokenExpiration))
		                      .compact();
		
		return RefreshToken.builder()
		                   .token(tokenString)
		                   .expiration((long) jwtConfiguration.refreshTokenExpiration)
		                   .build();
	}
	
	private RefreshToken createRefreshToken(String refreshToken) {
		var claims = parseJWT(refreshToken, jwtConfiguration.getRefreshTokenKey());
		var tokenString = Jwts.builder()
		                      .setClaims(claims.getBody())
		                      .compact();
		
		return RefreshToken.builder()
		                   .token(tokenString)
		                   .expiration((long) jwtConfiguration.refreshTokenExpiration)
		                   .build();
		
	}
	
	private AccessToken createAccessToken(String refreshToken) {
		var claims = parseJWT(refreshToken, jwtConfiguration.getRefreshTokenKey());
		var tokenString = Jwts.builder()
		                      .signWith(jwtConfiguration.getAccessTokenKey())
		                      .setClaims(claims.getBody())
		                      .setIssuedAt(new java.util.Date())
		                      .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtConfiguration.accessTokenExpiration))
		                      .compact();
		
		return AccessToken.builder()
		                  .token(tokenString)
		                  .expiration((long) jwtConfiguration.accessTokenExpiration)
		                  .build();
	}
	
	private void invalidateRefreshTokenChain(String token) {
		var optionalRefreshToken = refreshTokenRepository.findById(token);
		if (optionalRefreshToken.isEmpty()) {
			return;
		}
		
		// TODO: Test the correctness of the algorithm
		var refreshToken = optionalRefreshToken.get();
		while (refreshToken.getSuccessor() != null) {
			var successor = refreshTokenRepository.findById(refreshToken.getSuccessor());
			if (successor.isPresent()) {
				refreshToken = successor.get();
				refreshTokenRepository.delete(refreshToken);
			}
		}
	}
}
