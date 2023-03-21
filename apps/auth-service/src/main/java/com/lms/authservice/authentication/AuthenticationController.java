package com.lms.authservice.authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/authenticate")
public class AuthenticationController {
	
	@PostMapping ("/")
	public String authenticate(@RequestBody AuthenticationDto authenticationDto) {
		if (authenticationDto.getUsername()
		                     .equals("admin") && authenticationDto.getPassword()
		                                                          .equals("admin"))
			return "authenticated";
		else {
			return "not authenticated";
		}
	}
	
}
