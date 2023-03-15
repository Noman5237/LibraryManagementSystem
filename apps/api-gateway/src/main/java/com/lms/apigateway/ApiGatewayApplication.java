package com.lms.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
	
	@Value("${hosts.user-management-service}")
	private String userManagementServiceHost;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
		ApiGatewayApplication app = new ApiGatewayApplication();
		System.out.println("===================================");
		System.out.println(app.userManagementServiceHost);
	}
	
}
