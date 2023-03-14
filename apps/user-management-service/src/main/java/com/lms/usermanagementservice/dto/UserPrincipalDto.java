package com.lms.usermanagementservice.dto;

import com.lms.usermanagementservice.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPrincipalDto {
	
	private String email;
	private UserRole userRole;
}
