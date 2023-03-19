package com.test.blog.api.config;

import static com.test.blog.core.exception.ErrorCode.*;

import com.test.blog.api.dto.BlogApiErrorResponse;
import com.test.blog.core.exception.BlogException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlogApiExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            sb.append(violation.getPropertyPath().toString()).append(":").append(violation.getMessage()).append(",");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BlogApiErrorResponse.builder()
            .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(sb.deleteCharAt(sb.length() - 1).toString())
            .build());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BlogApiErrorResponse> exceptionHandle(Throwable e) {
        return ResponseEntity.status(INTERNAL_ERROR.getStatus()).body(BlogApiErrorResponse.builder()
            .errorCode(INTERNAL_ERROR.getErrorCode())
            .message(INTERNAL_ERROR.getMessage())
            .build());
    }

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<BlogApiErrorResponse> exceptionHandle(BlogException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(BlogApiErrorResponse.builder()
            .errorCode(e.getErrorCode())
            .message(e.getMessage())
            .build());
    }

}