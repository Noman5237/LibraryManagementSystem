package com.lms.usermanagementservice.user.service;

import com.lms.usermanagementservice.user.UserRepository;
import com.lms.usermanagementservice.user.dto.SignupDto;
import com.lms.usermanagementservice.user.exception.UserAlreadyExistsException;
import com.lms.usermanagementservice.user.exception.UserNotFoundException;
import com.lms.usermanagementservice.user.model.AccountStatus;
import com.lms.usermanagementservice.user.model.User;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserActivationService {
	
	// TODO: Most of the operations can be done in a single pass. Optimize later
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@SneakyThrows
	@Override
	public User signUp(SignupDto signupDto) {
		var existingUser = userRepository.findById(signupDto.getEmail());
		if (existingUser.isPresent()) {
			throw new UserAlreadyExistsException(signupDto.getEmail());
		}
		var user = User.builder()
		               .email(signupDto.getEmail())
		               .firstName(signupDto.getFirstName())
		               .lastName(signupDto.getLastName())
		               .dob(signupDto.getDob())
		               .hashedPassword(hashPassword(signupDto.getPassword()))
		               .userRole(signupDto.getUserRole())
		               .accountStatus(AccountStatus.PENDING)
		               .build();
		return userRepository.save(user);
	}
	
	@Override
	public User getUser(String email) {
		return userRepository.findById(email)
		                     .orElseThrow(() -> new UserNotFoundException(email));
	}
	
	@Override
	public User deleteUser(String email) {
		var user = getUser(email);
		userRepository.deleteById(user.getEmail());
		return user;
	}
	
	@Override
	public void activateUser(String email) {
		var user = getUser(email);
		user.setAccountStatus(AccountStatus.ACTIVE);
		userRepository.save(user);
	}
	
	@Override
	public void suspendUser(String email) {
		var user = getUser(email);
		user.setAccountStatus(AccountStatus.SUSPENDED);
		userRepository.save(user);
	}
	
	// FIXME: This is a temporary implementation. It should be replaced with a proper hashing algorithm.
	private String hashPassword(String password) {
		return password;
	}
	
	@Override
	public boolean checkHashedPassword(String hashedPassword, String password) {
		return hashedPassword.equals(hashPassword(password));
	}
	
}
