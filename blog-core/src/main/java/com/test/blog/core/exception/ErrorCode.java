package com.test.blog.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    FAILED_TO_LOAD_BLOG("BE91", HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load blog. Please contact the administrator."),
    INTERNAL_ERROR("BE99", HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error has occurred. Please contact the administrator.");

    private final String errorCode;
    private final HttpStatus status;
    private final String message;

    ErrorCode(String errorCode, HttpStatus status, String message) {
        this.errorCode = errorCode;
        this.status = status;
        this.message = message;
    }
}
