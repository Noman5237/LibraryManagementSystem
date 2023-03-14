package com.lms.usermanagementservice.service;

import com.lms.usermanagementservice.dto.CheckPasswordAuthenticationDto;
import com.lms.usermanagementservice.dto.SignupDto;
import com.lms.usermanagementservice.model.User;

public interface UserService {
	
	User signUp(SignupDto signupDto);
	
	boolean checkPasswordAuthentication(CheckPasswordAuthenticationDto checkPasswordAuthenticationDto);
	
	User getUser(String email);
	
	User deleteUser(String email);
}
