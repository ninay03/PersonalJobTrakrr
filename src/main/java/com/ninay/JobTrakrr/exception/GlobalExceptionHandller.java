package com.ninay.JobTrakrr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandller {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ErrorResponse response = new ErrorResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation failed");
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(errors);

        return response;
    }
    @ExceptionHandler(ApplicationNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleApplicationNotFound(
            ApplicationNotFound exception) {

        ErrorResponse response = new ErrorResponse();

        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(null);

        return response;
    }
    @ExceptionHandler(InterviewRoundNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleInterviewRoundNotFound(
            InterviewRoundNotFound exception) {

        ErrorResponse response = new ErrorResponse();

        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(null);

        return response;
    }
    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<String> handleInvalidStatusTransition(
            InvalidStatusTransitionException ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

}