package com.lms.usermanagementservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
