package com.pribavkindenis.requesthub.integration.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pribavkindenis.requesthub.model.dto.ApiErrorResponse;
import com.pribavkindenis.requesthub.model.enumerate.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleDefault(Exception e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ApiError.UNEXPECTED, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ApiError.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException e) {
        return buildResponse(HttpStatus.FORBIDDEN, ApiError.FORBIDDEN, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var errorMessage = buildValidationErrorMessage(e.getBindingResult().getAllErrors());
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ApiError.UNPROCESSABLE_JSON, errorMessage);
    }

    private String buildValidationErrorMessage(List<ObjectError> errors) {
        return errors.stream()
                     .map(this::getObjectErrorMessage)
                     .collect(Collectors.joining("; "));
    }

    private String getObjectErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            var fe = (FieldError) e;
            return String.format("Field '%s' %s", fe.getField(), fe.getDefaultMessage());
        } else {
            return String.format("Object '%s' %s", e.getObjectName(), e.getDefaultMessage());
        }
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ResponseEntity<Object> responseEntity;
        if (e.getCause() instanceof JsonProcessingException) {
            responseEntity = buildResponse(HttpStatus.BAD_REQUEST, ApiError.INVALID_JSON, "Error while processing JSON object");
        } else {
            responseEntity = buildResponse(HttpStatus.BAD_REQUEST, ApiError.BAD_REQUEST, e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, ApiError error, String description) {
        var apiError = ApiErrorResponse.builder()
                                       .status(status.value())
                                       .timestamp(LocalDateTime.now())
                                       .error(error)
                                       .description(description)
                                       .build();
        return ResponseEntity.status(status).body(apiError);
    }
}
