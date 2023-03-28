package com.lms.usermanagementservice.user.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	
	private String email;
	private String firstName;
	private String lastName;
}
