package com.lms.usermanagementservice.service;

import com.lms.usermanagementservice.dto.SignupDto;
import com.lms.usermanagementservice.model.User;

public interface UserService {
	
	User signUp(SignupDto signupDto);
	
	User getUser(String email);
	
	User getUserPrincipal(String email, String password);
	
	User deleteUser(String email);
}
