package com.lms.authservice.authentication.service.external;

import com.lms.authservice.dto.UserPrincipalDto;

import javax.servlet.http.HttpServletRequest;

public interface UserManagementService {
	
	UserPrincipalDto getUserPrincipal(HttpServletRequest originalRequest);
}
