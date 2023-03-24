package com.lms.restexception.advice;

import com.lms.restexception.exception.RESTException;
import com.lms.restexception.dto.ExceptionResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RESTExceptionAdvice {
	
	@ExceptionHandler (RESTException.class)
	public ExceptionResponseDto handleRESTException(RESTException exception) {
		return ExceptionResponseDto.builder()
		                           .message(exception.getMessage())
		                           .errors(exception.getPayload() == null ?
				                                   null : exception.getPayload()
				                                                   .entrySet()
				                                                   .stream()
				                                                   .collect(HashMap::new,
				                                                            (map, entry) -> map.put(
						                                                            entry.getKey(),
						                                                            entry.getValue()
						                                                                 .toString()),
				                                                            Map::putAll))
		                           .build();
	}
}