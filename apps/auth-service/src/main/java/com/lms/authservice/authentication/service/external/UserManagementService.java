package com.lms.authservice.authentication.service.external;

import com.lms.authservice.authentication.dto.UserPrincipalDto;

import javax.servlet.http.HttpServletRequest;

public interface UserManagementService {
	
	UserPrincipalDto getUserPrincipal(HttpServletRequest originalRequest);
}
