package com.lms.usermanagementservice.dto;

import com.lms.usermanagementservice.model.User;
import com.lms.usermanagementservice.model.UserRole;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class SignupDto implements Serializable {
	
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
	
	@NotNull
	private final Date dob;
	
	@NotBlank
	@Length (min = 8, max = 20)
	private final String password;
	
	private final UserRole userRole;
}