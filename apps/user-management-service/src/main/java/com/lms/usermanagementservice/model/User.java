package com.lms.usermanagementservice.model;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Table (name = "user")
public class User {
	
	@Id
	@Column (nullable = false)
	private String email;
	
	@Column (nullable = false)
	private String firstName;
	
	@Column (nullable = false)
	private String lastName;
	
	@Column (nullable = false)
	private String dob;
	
	@Column (nullable = false)
	private String hashedPassword;
	
	@Column (nullable = false)
	@Enumerated (STRING)
	private AccountStatus accountStatus;
	
	@Column (nullable = false)
	@Enumerated (STRING)
	private UserRole userRole;
}
