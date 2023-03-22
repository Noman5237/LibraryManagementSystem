package com.lms.authservice.authentication;

import com.lms.authservice.dto.TokenPairDto;
import com.lms.authservice.jwt.JWTService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/authenticate")
public class AuthenticationController {
	
	private final JWTService jwtService;
	
	public AuthenticationController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping ("/")
	public TokenPairDto authenticate(@RequestBody AuthenticationDto authenticationDto) {
		String email = authenticationDto.getEmail();
		if (email.equals("admin") && authenticationDto.getPassword()
		                                              .equals("admin")) {
			var refreshToken = jwtService.generateRefreshToken(email);
			var accessToken = jwtService.generateAccessToken(refreshToken);
			return new TokenPairDto(accessToken, refreshToken);
		}
		
		// FIXME use the generic rest exceptions
		throw new RuntimeException("Invalid credentials");
	}
	
	@PostMapping ("/refresh")
	public TokenPairDto refresh(@RequestBody String refreshToken) {
		if (jwtService.validateRefreshToken(refreshToken)) {
			var newRefreshToken = jwtService.generateRefreshToken(refreshToken);
			var accessToken = jwtService.generateAccessToken(newRefreshToken);
			return new TokenPairDto(newRefreshToken, accessToken);
		}
		
		// FIXME use the generic rest exceptions
		throw new RuntimeException("Invalid refresh token");
	}
	
	@PostMapping ("/access-token")
	public TokenPairDto accessToken(@RequestBody String refreshToken) {
		if (jwtService.validateRefreshToken(refreshToken)) {
			var accessToken = jwtService.generateAccessToken(refreshToken);
			return new TokenPairDto(refreshToken, accessToken);
		}
		
		// FIXME use the generic rest exceptions
		throw new RuntimeException("Invalid refresh token");
	}
	
}
