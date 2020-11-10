package br.com.jkassner.apiloteria.handlers;

import br.com.jkassner.apiloteria.exceptions.DezenaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.sentry.SentryClient;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

	@Autowired
	SentryClient sentryClient;

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		sentryClient.sendException(ex);

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(DezenaInvalidaException.class)
	protected ResponseEntity<Object> dezenaInvalidaException(DezenaInvalidaException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
