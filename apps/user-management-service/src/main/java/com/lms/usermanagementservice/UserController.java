package com.lms.usermanagementservice;

import com.lms.usermanagementservice.dto.UserDto;
import com.lms.usermanagementservice.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping ("/user")
public class UserController {
	
	@PostMapping ("/signup")
	public User signup(@Valid @RequestBody UserDto user) {
		System.out.println(user);
		return null;
	}
}
