package com.lms.authservice.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserPrincipalDto {
	
	private String email;
	private UserRole userRole;
	
	public enum UserRole {
		LIBRARY_SUPERVISOR, LIBRARIAN, FACULTY, STUDENT
	}
}
