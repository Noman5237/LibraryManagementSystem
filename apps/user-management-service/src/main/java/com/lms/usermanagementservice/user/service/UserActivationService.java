package com.lms.usermanagementservice.user.service;

public interface UserActivationService {
	
	void activateUser(String email);
	
	void suspendUser(String email);
}