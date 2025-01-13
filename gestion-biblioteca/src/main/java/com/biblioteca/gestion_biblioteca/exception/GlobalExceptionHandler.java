package com.biblioteca.gestion_biblioteca.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParam(MissingServletRequestParameterException ex) {
        
        String message = "Error: El parámetro requerido '" + ex.getParameterName() + "' no está presente.";
        
        return ResponseEntity.badRequest().body(message);
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        
        String message = "Los campos obligatorios no pueden ser nulos.";
        
        return ResponseEntity.status(400).body(message);
    }
}
