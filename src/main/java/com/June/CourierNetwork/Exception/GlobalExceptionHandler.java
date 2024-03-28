package com.June.CourierNetwork.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Error: Duplicate entry for key '" + extractUniqueConstraintName(ex.getMessage()) + "'";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    private String extractUniqueConstraintName(String message) {
        // Extract the unique constraint name from the exception message
        // This is a simplified example; you may need to adjust this based on the actual format of the error message
        int startIndex = message.indexOf("for key '") + "for key '".length();
        int endIndex = message.indexOf("'", startIndex);
        return message.substring(startIndex, endIndex);
    }
}

