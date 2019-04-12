package com.inmaytide.orbit.commons.exception;

import java.time.Instant;

public class DefaultRuntimeException extends RuntimeException {

    private final Integer httpStatus;

    private final String code;

    private final Long timestamp;

    public DefaultRuntimeException() {
        this(500, "err_unexpected_exception");
    }

    public DefaultRuntimeException(String code) {
        this(500, code);
    }

    public DefaultRuntimeException(Integer httpStatus, String code) {
        this(httpStatus, code, null);
    }

    public DefaultRuntimeException(Integer httpStatus, String code, String message) {
        super(message);
        this.timestamp = Instant.now().toEpochMilli();
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public DefaultRuntimeException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.timestamp = Instant.now().toEpochMilli();
        this.httpStatus = 500;
        this.code = "err_unexpected_exception";
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}
