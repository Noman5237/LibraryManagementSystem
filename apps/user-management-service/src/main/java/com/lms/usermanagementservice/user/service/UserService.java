package com.lms.usermanagementservice.user.service;

import com.lms.usermanagementservice.user.dto.SignupDto;
import com.lms.usermanagementservice.user.model.User;

public interface UserService {
	
	User signUp(SignupDto signupDto);
	
	User getUser(String email);
	
	User deleteUser(String email);
	
	boolean checkHashedPassword(String hashedPassword, String password);
}
