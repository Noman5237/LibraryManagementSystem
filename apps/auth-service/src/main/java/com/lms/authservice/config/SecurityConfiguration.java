package com.lms.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// permit public access with no restrictions on authenticate request
		http.csrf()
		    .disable()
		    .authorizeRequests()
		    .antMatchers("/authenticate/**")
		    .permitAll()
		    .anyRequest()
		    .authenticated();
		return http.build();
	}
}
