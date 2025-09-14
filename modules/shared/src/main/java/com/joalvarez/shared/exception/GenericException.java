package com.joalvarez.shared.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {

	private final HttpStatus status;
	private final int code;

	public GenericException(Throwable cause, HttpStatus status, int code) {
		super(cause);
		this.status = status;
		this.code = code;
	}

	public GenericException(HttpStatus status, int code, String message) {
		super(message);
		this.status = status;
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}
}