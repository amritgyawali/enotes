package com.amrit.exception;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private Map<String, Object> error;

	public ValidationException(Map<String, Object> error) {
		super("Validation failed");
		this.error = error;
	}

	public String getErrors() {
		return error.toString();
	}

}
