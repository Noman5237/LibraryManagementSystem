package com.lms.authservice.authentication;

import com.lms.authservice.authentication.service.AuthenticationService;
import com.lms.authservice.authentication.service.external.UserManagementService;
import com.lms.authservice.jwt.JWTService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping ("/authenticate")
public class AuthenticationController {
	
	private final JWTService jwtService;
	private final UserManagementService userManagementService;
	
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(JWTService jwtService, UserManagementService userManagementService,
	                                AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.userManagementService = userManagementService;
		this.authenticationService = authenticationService;
	}
	
	@PostMapping ("/")
	@io.swagger.v3.oas.annotations.parameters.RequestBody (required = true,
			content = @Content (mediaType = "application/json",
					schema = @Schema (implementation = Map.class),
					examples = @ExampleObject (value = "{\n" + "  \"email\": \"admin@lms.edu\",\n" + "  \"password\": \"12345678\"\n" + "}")))
	@Parameter (name = "strategy", description = "Authentication strategy", required = true, example = "local")
	public ResponseEntity<String> authenticate(HttpServletRequest request) {
		var userPrincipal = userManagementService.getUserPrincipal(request);
		
		var refreshToken = jwtService.generateRefreshToken(userPrincipal);
		var accessToken = jwtService.generateAccessToken(refreshToken);
		
		return ResponseEntity.ok()
		                     .header(SET_COOKIE, authenticationService.getRefreshTokenCookie(refreshToken))
		                     .body(accessToken);
	}
	
	@PostMapping ("/refresh")
	public ResponseEntity<String> refresh(@CookieValue (value = "refreshToken", required = false) String refreshToken) {
		var newRefreshToken = jwtService.refreshToken(refreshToken);
		var accessToken = jwtService.generateAccessToken(newRefreshToken);
		
		return ResponseEntity.ok()
		                     .header(SET_COOKIE, authenticationService.getRefreshTokenCookie(newRefreshToken))
		                     .body(accessToken);
	}
	
	@PostMapping ("/sign-out")
	public void signOut(@CookieValue (value = "refreshToken", required = false) String refreshToken) {
		jwtService.invalidateRefreshTokenChain(refreshToken);
	}
	
	@PostMapping ("/sign-out-all")
	public void signOutAll(@CookieValue (value = "refreshToken", required = false) String refreshToken) {
		jwtService.invalidateAllRefreshTokenChains(refreshToken);
	}
	
}
