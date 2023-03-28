package com.lms.usermanagementservice.user.dto;

import com.lms.usermanagementservice.user.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPrincipalDto {
	
	private String email;
	private UserRole userRole;
}
