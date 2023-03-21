package com.lms.authservice.authorization;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/authorize")
public class AuthorizationController {
	
	@PostMapping ("/check-jwt")
	public boolean checkJwt(@RequestBody String jwt) {
		return jwt.equals("valid");
	}
}
