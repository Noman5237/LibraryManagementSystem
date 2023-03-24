package com.lms.usermanagementservice.dto;

import com.lms.usermanagementservice.model.User;

public interface AuthenticationDto {
	
	boolean isAuthenticated(User user);
}
