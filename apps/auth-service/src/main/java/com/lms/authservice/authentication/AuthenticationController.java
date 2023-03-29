package com.lms.authservice.authentication;

import com.lms.authservice.dto.TokenPairDto;
import com.lms.authservice.jwt.JWTService;
import com.lms.authservice.authentication.service.external.UserManagementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping ("/authenticate")
public class AuthenticationController {
	
	private final JWTService jwtService;
	private final UserManagementService userManagementService;
	
	public AuthenticationController(JWTService jwtService, UserManagementService userManagementService) {
		this.jwtService = jwtService;
		this.userManagementService = userManagementService;
	}
	
	@PostMapping ("/")
	public TokenPairDto authenticate(HttpServletRequest request) {
		var userPrincipal = userManagementService.getUserPrincipal(request);
		var refreshToken = jwtService.generateRefreshToken(userPrincipal);
		var accessToken = jwtService.generateAccessToken(refreshToken);
		return new TokenPairDto(refreshToken, accessToken);
	}
	
	@PostMapping ("/refresh")
	public TokenPairDto refresh(@RequestBody String refreshToken) {
		var newRefreshToken = jwtService.refreshToken(refreshToken);
		var accessToken = jwtService.generateAccessToken(newRefreshToken);
		return new TokenPairDto(newRefreshToken, accessToken);
	}
	
	@PostMapping ("/access-token")
	public TokenPairDto accessToken(@RequestBody String refreshToken) {
		var accessToken = jwtService.generateAccessToken(refreshToken);
		return new TokenPairDto(refreshToken, accessToken);
	}
	
}
