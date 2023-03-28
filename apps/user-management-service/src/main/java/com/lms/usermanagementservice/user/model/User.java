package com.lms.usermanagementservice.user.model;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.EnumType.STRING;

@Entity
@Table (name = "user", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
	
	@Id
	@Column (nullable = false)
	private String email;
	
	@Column (nullable = false)
	private String firstName;
	
	@Column (nullable = false)
	private String lastName;
	
	@Column (nullable = false)
	private Date dob;
	
	@Column (nullable = false)
	private String hashedPassword;
	
	@Column (nullable = false)
	@Enumerated (STRING)
	private AccountStatus accountStatus;
	
	@Column (nullable = false)
	@Enumerated (STRING)
	private UserRole userRole;
}
