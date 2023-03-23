package com.lms.authservice.jwt;

import com.lms.authservice.jwt.tokens.accesstoken.AccessToken;
import com.lms.authservice.jwt.tokens.refreshtoken.RefreshToken;
import com.lms.authservice.jwt.tokens.refreshtoken.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

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
		var isValid = validateRefreshToken(refreshToken);
		if (!isValid) {
			return null;
		}
		var accessToken = createAccessToken(refreshToken);
		return accessToken.getToken();
	}
	
	
	@Override
	public String generateRefreshToken(String email) {
		var refreshToken = createRefreshToken(email);
		refreshTokenRepository.save(refreshToken);
		return refreshToken.getToken();
	}
	
	@Override
	public String refreshToken(String refreshToken) {
		var isVerified = validateRefreshToken(refreshToken);
		if (!isVerified) {
			return null;
		}
		var claims = parseJWT(refreshToken);
		var email = claims.getBody()
		                  .getSubject();
		var newRefreshToken = createRefreshToken(email);
		refreshTokenRepository.save(newRefreshToken);
		return newRefreshToken.getToken();
	}
	
	private boolean validateRefreshToken(String token) {
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
			if (!used) {
				return true;
			}
			
			// Delete all refresh tokens that are successors of this token
			while (storedToken.getSuccessor() != null) {
				var successor = refreshTokenRepository.findById(storedToken.getSuccessor());
				if (successor.isPresent()) {
					storedToken = successor.get();
					refreshTokenRepository.delete(storedToken);
				}
			}
			
			return false;
		} catch (JwtException e) {
			return false;
		}
	}
	
	private boolean validateAccessToken(String token) {
		try {
			parseJWT(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private Jws<Claims> parseJWT(String token) {
		return Jwts.parserBuilder()
		           .setSigningKey(jwtConfiguration.getRefreshTokenKey())
		           .build()
		           .parseClaimsJws(token);
	}
	
	private RefreshToken createRefreshToken(String email) {
		var tokenString = Jwts.builder()
		                      .signWith(jwtConfiguration.getRefreshTokenKey())
		                      .setSubject(email)
		                      .setIssuedAt(new java.util.Date())
		                      .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtConfiguration.refreshTokenExpiration))
		                      .compact();
		
		return RefreshToken.builder()
		                   .token(tokenString)
		                   .expiration((long) jwtConfiguration.refreshTokenExpiration)
		                   .build();
	}
	
	private AccessToken createAccessToken(String refreshToken) {
		var claims = parseJWT(refreshToken);
		var tokenString = Jwts.builder()
		                      .signWith(jwtConfiguration.getAccessTokenKey())
		                      .setSubject(claims.getBody()
		                                        .getSubject())
		                      .setIssuedAt(new java.util.Date())
		                      .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtConfiguration.accessTokenExpiration))
		                      .compact();
		
		return AccessToken.builder()
		                  .token(tokenString)
		                  .expiration((long) jwtConfiguration.accessTokenExpiration)
		                  .build();
	}
	
}
