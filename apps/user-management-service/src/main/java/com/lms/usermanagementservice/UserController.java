package com.lms.usermanagementservice;

import com.lms.usermanagementservice.dto.CheckPasswordAuthenticationDto;
import com.lms.usermanagementservice.dto.SignupDto;
import com.lms.usermanagementservice.dto.UserDto;
import com.lms.usermanagementservice.dto.UserPrincipalDto;
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
	public UserPrincipalDto getUserPrincipal(@Valid @RequestBody CheckPasswordAuthenticationDto checkPasswordAuthenticationDto) {
		var isValidated = userService.checkPasswordAuthentication(checkPasswordAuthenticationDto);
		var user = userService.getUser(checkPasswordAuthenticationDto.getEmail());
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
