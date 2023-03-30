package com.lms.usermanagementservice.user;

import com.lms.usermanagementservice.authentication.AuthenticationStrategies;
import com.lms.usermanagementservice.user.dto.SignupDto;
import com.lms.usermanagementservice.user.dto.UserDto;
import com.lms.usermanagementservice.user.dto.UserPrincipalDto;
import com.lms.usermanagementservice.user.model.User;
import com.lms.usermanagementservice.user.service.UserActivationService;
import com.lms.usermanagementservice.user.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

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
	
	@PostMapping ("/principal")
	@io.swagger.v3.oas.annotations.parameters.RequestBody (required = true,
			content = @Content (mediaType = "application/json",
					schema = @Schema (implementation = Map.class),
					examples = @ExampleObject (value = "{\n" + "  \"email\": \"admin@lms.edu\",\n" + "  \"password\": \"12345678\"\n" + "}")))
	public UserPrincipalDto getUserPrincipal(HttpServletRequest request, @RequestParam String strategy) {
		var user = AuthenticationStrategies.getAuthenticatedUser(strategy, request);
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
