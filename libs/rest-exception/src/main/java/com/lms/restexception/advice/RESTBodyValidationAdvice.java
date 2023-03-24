package com.lms.restexception.advice;

import com.lms.restexception.dto.ExceptionResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RESTBodyValidationAdvice {

	public static final Logger LOGGER = LoggerFactory.getLogger(RESTBodyValidationAdvice.class);
	
	@ExceptionHandler ({MethodArgumentNotValidException.class})
	@ResponseStatus (HttpStatus.BAD_REQUEST)
	public ExceptionResponseDto handleValidationError(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();
		if (exception.hasFieldErrors()) {
			for (FieldError error : exception.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
		}
		LOGGER.error("Validation error: {}", errors);
		return ExceptionResponseDto.builder()
		                           .errors(errors)
		                           .build();
	}
}
