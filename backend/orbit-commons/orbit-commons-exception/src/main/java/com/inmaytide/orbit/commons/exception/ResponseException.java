package com.inmaytide.orbit.commons.exception;

import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;
import org.springframework.http.HttpStatus;

public abstract class ResponseException extends RuntimeException {

    private static final long serialVersionUID = 5787716233351989628L;

    private final String code;
    private final HttpStatus httpStatus;

    public ResponseException(ResponseDefinition definition) {
        this.code = definition.getCode();
        this.httpStatus = definition.getStatus();
    }

    public ResponseException(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
