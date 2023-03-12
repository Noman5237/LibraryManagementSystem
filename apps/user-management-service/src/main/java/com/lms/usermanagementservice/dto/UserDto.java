package com.lms.usermanagementservice.dto;

import com.lms.usermanagementservice.model.User;
import com.lms.usermanagementservice.model.UserRole;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {
	
	@NotBlank
	@Email (regexp = "^[a-zA-Z0-9_.+-]+@lms.edu$",
			message = "Email must be a valid LMS email address")
	private final String email;
	
	@NotBlank
	@Length (min = 3, max = 20)
	private final String firstName;
	
	@NotBlank
	@Length (min = 3, max = 20)
	private final String lastName;
	
	@NotBlank
	private final String dob;
	
	@NotBlank
	@Length (min = 8, max = 20)
	private final String password;
	
	private final UserRole userRole;
}