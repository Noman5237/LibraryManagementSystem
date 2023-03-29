package com.lms.authservice.jwt;

import com.lms.authservice.dto.UserPrincipalDto;
import com.lms.authservice.jwt.tokens.accesstoken.AccessToken;
import com.lms.authservice.jwt.tokens.refreshtoken.RefreshToken;
import com.lms.authservice.jwt.tokens.refreshtoken.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
		var isValid = validateRefreshToken(refreshToken);
		if (!isValid) {
			// FIXME: throw exception
			return null;
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
		var isVerified = validateRefreshToken(refreshToken);
		if (!isVerified) {
			// FIXME: throw exception
			return null;
		}
		var claims = parseJWT(refreshToken, jwtConfiguration.getRefreshTokenKey());
		var email = claims.getBody()
		                  .getSubject();
		// FIXME: get the roles from the claims
		var userPrincipal = UserPrincipalDto.builder()
		                                    .email(email)
		                                    .build();
		var newRefreshToken = createRefreshToken(userPrincipal);
		refreshTokenRepository.save(newRefreshToken);
		return newRefreshToken.getToken();
	}
	
	public boolean validateAccessToken(String token) {
		try {
			parseJWT(token, jwtConfiguration.getAccessTokenKey());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean validateRefreshToken(String token) {
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
			
			// FIXME: refactor out the side effect
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
	
	private Jws<Claims> parseJWT(String token, Key signingKey) {
		// FIXME: throw invalid refresh token rest exception
		return Jwts.parserBuilder()
		           .setSigningKey(signingKey)
		           .build()
		           .parseClaimsJws(token);
	}
	
	private RefreshToken createRefreshToken(UserPrincipalDto userPrincipal) {
		var tokenString = Jwts.builder()
		                      .signWith(jwtConfiguration.getRefreshTokenKey())
		                      .setSubject(userPrincipal.getEmail())
		                      .setIssuedAt(new java.util.Date())
		                      .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtConfiguration.refreshTokenExpiration))
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
	
}
