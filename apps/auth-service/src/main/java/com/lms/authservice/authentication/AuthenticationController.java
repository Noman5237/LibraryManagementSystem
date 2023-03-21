package com.lms.authservice.authentication;

import com.lms.authservice.dto.TokenDto;
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
	public TokenDto authenticate(@RequestBody AuthenticationDto authenticationDto) {
		String email = authenticationDto.getEmail();
		if (email.equals("admin") && authenticationDto.getPassword()
		                                              .equals("admin")) {
			var token = jwtService.generateRefreshToken(email);
			return new TokenDto(token);
		}
		
		// FIXME use the generic rest exceptions
		throw new RuntimeException("Invalid credentials");
	}
	
	@PostMapping ("/refresh")
	public TokenDto refresh(@RequestBody String refreshToken) {
		if (jwtService.validateRefreshToken(refreshToken)) {
			var token = jwtService.generateRefreshToken(refreshToken);
			return new TokenDto(token);
		}
		
		// FIXME use the generic rest exceptions
		throw new RuntimeException("Invalid refresh token");
	}
	
	@PostMapping ("/access-token")
	public TokenDto accessToken(@RequestBody String refreshToken) {
		if (jwtService.validateRefreshToken(refreshToken)) {
			var token = jwtService.generateAccessToken(refreshToken);
			return new TokenDto(token);
		}
		
		// FIXME use the generic rest exceptions
		throw new RuntimeException("Invalid refresh token");
	}
	
}
