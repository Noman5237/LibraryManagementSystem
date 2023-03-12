package com.lms.usermanagementservice.core.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class ExceptionResponseDto {
	
	private String message;
	private Map<String, String> errors;
	
}
