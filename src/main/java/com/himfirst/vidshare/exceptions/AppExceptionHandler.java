package com.himfirst.vidshare.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(404, new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidValuesException.class)
	public ResponseEntity<?> handleInvalidValuesException(InvalidValuesException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(400, new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmptyListException.class)
	public ResponseEntity<?> handleEmptyListException(EmptyListException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(204, new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(ApiResponseException.class)
	public ResponseEntity<?> apiResponseException(ApiResponseException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(400, new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { IllegalStateException.class })
	public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception, WebRequest request) {

		ErrorDetails errorMessage = new ErrorDetails(500, new Date(), exception.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)//PSQLException
	public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
		// Customize the response based on your requirements
		return new ResponseEntity<>("Constraint violation: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	}


}
