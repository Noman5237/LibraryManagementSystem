package com.lms.authservice.authorization;

import com.lms.authservice.jwt.JWTService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/authorize")
public class AuthorizationController {
	
	private final JWTService jwtService;
	
	public AuthorizationController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping ("/check-access-token")
	public boolean checkJwt(@RequestBody String jwt) {
		return jwtService.validateAccessToken(jwt);
	}
}
