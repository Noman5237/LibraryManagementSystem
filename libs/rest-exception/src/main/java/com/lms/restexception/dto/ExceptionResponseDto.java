package com.lms.restexception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
public class ExceptionResponseDto {
	
	private String message;
	private Map<String, String> errors;
	
}
