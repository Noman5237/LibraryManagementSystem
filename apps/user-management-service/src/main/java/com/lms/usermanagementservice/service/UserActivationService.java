package com.lms.usermanagementservice.service;

public interface UserActivationService {
	
	void activateUser(String email);
	
	void suspendUser(String email);
}