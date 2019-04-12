package com.inmaytide.orbit.commons.exception;

public class BadRequestException extends DefaultRuntimeException {

    public BadRequestException() {
        super(400, "err_bad_request");
    }

    public BadRequestException(String message) {
        super(400, "err_bad_request", message);
    }

}
