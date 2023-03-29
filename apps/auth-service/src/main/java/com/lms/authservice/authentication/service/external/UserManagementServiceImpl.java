package com.lms.authservice.authentication.service.external;

import com.lms.authservice.dto.UserPrincipalDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserManagementServiceImpl implements UserManagementService {
	
	@Override
	public UserPrincipalDto getUserPrincipal(HttpServletRequest originalRequest) {
		return null;
	}
}
