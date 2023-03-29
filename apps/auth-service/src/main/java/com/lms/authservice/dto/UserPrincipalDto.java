package com.lms.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPrincipalDto {
	
	private String email;
	private UserRole userRole;
}
