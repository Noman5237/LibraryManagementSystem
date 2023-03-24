package com.lms.usermanagementservice.dto;

import com.lms.usermanagementservice.model.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CheckPasswordAuthenticationDto implements AuthenticationDto, Serializable {
	
	@NotBlank
	@Email (regexp = "^[a-zA-Z0-9_.+-]+@lms.edu$", message = "Email must be a valid LMS email address")
	private final String email;
	
	@NotBlank
	@Length (min = 8, max = 20)
	private final String password;
	
	@Override
	public boolean isAuthenticated(User user) {
		return user.getEmail().equals(email) && user.getHashedPassword().equals(password);
	}
}
