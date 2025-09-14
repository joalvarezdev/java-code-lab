package com.joalvarez.shared.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joalvarez.shared.LoggerHelper;
import com.joalvarez.shared.data.dto.general.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerException implements LoggerHelper {

	private final ObjectMapper mapper;

	public GlobalHandlerException (ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ResponseDTO> handler(GenericException e) throws JsonProcessingException {
		return ResponseEntity.badRequest().body(new ResponseDTO(
			e.getStatus().name(),
			e.getMessage(),
			this.mapper.readTree(this.mapper.writeValueAsString(e)),
			null,
			LocalDateTime.now()
		));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseDTO handler(HttpMessageNotReadableException e) throws JsonProcessingException {
		this.error("{}", e.getMessage());
		return new ResponseDTO(
			HttpStatus.BAD_REQUEST.name(),
			e.getMessage(),
			null,
			null,
			LocalDateTime.now()
		);
	}
}