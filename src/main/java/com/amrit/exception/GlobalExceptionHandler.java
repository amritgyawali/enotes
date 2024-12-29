package com.amrit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		log.error("GlobalExceptionHandler :: handleException ::", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e) {
		log.error("GlobalExceptionHandler :: handleNullPointerException ::", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException ::", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e) {
		return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

}
