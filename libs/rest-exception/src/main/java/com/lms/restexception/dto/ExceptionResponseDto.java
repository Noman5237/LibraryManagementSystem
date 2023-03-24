package com.lms.restexception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
@Builder
public class ExceptionResponseDto {
	
	private String message;
	private Map<String, String> errors;
	
}
