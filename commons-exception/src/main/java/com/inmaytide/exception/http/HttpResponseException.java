package com.inmaytide.orbit.commons.exception.http;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class HttpResponseException extends RuntimeException {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String DEFAULT_CODE = "err_unexpected_exception";

    private final HttpStatus status;

    private final String code;

    private final Long timestamp;

    public HttpResponseException() {
        this(DEFAULT_STATUS, DEFAULT_CODE);
    }

    public HttpResponseException(HttpStatus httpStatus, String code) {
        this(httpStatus, code, null);
    }

    public HttpResponseException(HttpStatus httpStatus, String code, String message) {
        super(message);
        this.timestamp = Instant.now().toEpochMilli();
        this.status = httpStatus;
        this.code = code;
    }

    public HttpResponseException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.timestamp = Instant.now().toEpochMilli();
        this.status = DEFAULT_STATUS;
        this.code = DEFAULT_CODE;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}
