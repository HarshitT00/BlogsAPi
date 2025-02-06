package com.harshit.blogs.exceptionHandling;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public enum ErrorCode {
    RESOURCE_NOT_FOUND("ERR_001", HttpStatus.NOT_FOUND),
    VALIDATION_FAILED("ERR_002", HttpStatus.BAD_REQUEST),
    BUSINESS_ERROR("ERR_003", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAILED("ERR_004", HttpStatus.UNAUTHORIZED),
    INTERNAL_ERROR("ERR_005", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final HttpStatus status;

    ErrorCode(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }
}
