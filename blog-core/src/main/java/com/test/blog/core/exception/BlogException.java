package com.test.blog.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class BlogException extends RuntimeException{

    private final String errorCode;

    private final HttpStatus httpStatus;

    private final String message;

    public BlogException(ErrorCode errorCode) {
        this(errorCode.getStatus(), errorCode.getErrorCode(), errorCode.getMessage());
    }

    public BlogException(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

}
