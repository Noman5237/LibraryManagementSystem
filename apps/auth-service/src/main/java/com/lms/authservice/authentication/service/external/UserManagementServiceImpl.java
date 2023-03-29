package com.lms.authservice.authentication.service.external;

import com.lms.authservice.authentication.exceptions.AuthenticationException;
import com.lms.authservice.authentication.dto.UserPrincipalDto;
import com.lms.restexception.dto.ExceptionResponseDto;
import com.lms.restexception.exception.RESTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserManagementServiceImpl implements UserManagementService {
	
	private final WebClient.Builder webClientBuilder;
	
	public UserManagementServiceImpl(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}
	
	@Override
	public UserPrincipalDto getUserPrincipal(HttpServletRequest originalRequest) {
		try {
			String body = originalRequest.getReader()
			                             .lines()
			                             .reduce("", (accumulator, actual) -> accumulator + actual);
			
			String requestParams = originalRequest.getQueryString();
			var response = webClientBuilder.baseUrl("http://user-management-service")
			                               .build()
			                               .post()
			                               .uri("/api/user/principal/?" + requestParams)
			                               .contentType(MediaType.valueOf(originalRequest.getContentType()))
			                               .bodyValue(body)
			                               .retrieve()
			                               .onStatus(HttpStatus::isError,
			                                         clientResponse -> clientResponse.bodyToMono(ExceptionResponseDto.class)
			                                                                         .handle((exceptionResponse, sink) -> {
				                                                                         System.out.println(
						                                                                         exceptionResponse.getMessage());
				                                                                         sink.error(new AuthenticationException(
						                                                                         exceptionResponse.getMessage(),
						                                                                         exceptionResponse.getErrors()));
			                                                                         }))
			                               .toEntity(UserPrincipalDto.class)
			                               .block()
			                               .getBody();
			return response;
		} catch (RESTException e) {
			throw e;
		} catch (Exception e) {
			throw new AuthenticationException();
		}
		
	}
}
