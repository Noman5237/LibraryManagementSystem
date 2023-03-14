package com.lms.usermanagementservice;

import com.lms.usermanagementservice.core.exception.RESTException;
import com.lms.usermanagementservice.dto.*;
import com.lms.usermanagementservice.model.User;
import com.lms.usermanagementservice.service.UserActivationService;
import com.lms.usermanagementservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping ("/user")
public class UserController {
	
	private final UserService userService;
	private final UserActivationService userActivationService;
	
	public UserController(UserService userService, UserActivationService userActivationService) {
		this.userService = userService;
		this.userActivationService = userActivationService;
	}
	
	@PostMapping ("/signup")
	public User signup(@Valid @RequestBody SignupDto signupDto) {
		return userService.signUp(signupDto);
	}
	
	@PostMapping ("/check-password-authentication")
	public boolean checkPasswordAuthentication(@Valid @RequestBody CheckPasswordAuthenticationDto checkPasswordAuthenticationDto) {
		return userService.checkPasswordAuthentication(checkPasswordAuthenticationDto);
	}
	
	@GetMapping ("/{email}")
	public UserDto getSimpleUser(@PathVariable String email) {
		var user = userService.getUser(email);
		return UserDto.builder()
		              .email(user.getEmail())
		              .firstName(user.getFirstName())
		              .lastName(user.getLastName())
		              .build();
	}
	
	@GetMapping ("/principal/{email}")
	public UserPrincipalDto getUserPrincipal(@PathVariable String email) {
		var user = userService.getUser(email);
		return UserPrincipalDto.builder()
		                       .email(user.getEmail())
		                       .userRole(user.getUserRole())
		                       .build();
	}
	
	@DeleteMapping ("/{email}")
	public User deleteUser(@PathVariable String email) {
		return userService.deleteUser(email);
	}
	
	@PutMapping ("/activate/{email}")
	public void activateUser(@PathVariable String email) {
		userActivationService.activateUser(email);
	}
	
	@PutMapping ("/suspend/{email}")
	public void suspendUser(@PathVariable String email) {
		userActivationService.suspendUser(email);
	}
	
}
