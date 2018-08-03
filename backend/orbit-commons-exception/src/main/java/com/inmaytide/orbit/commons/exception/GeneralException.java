package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

public class GeneralException extends RuntimeException {

    public static final String ERROR_UNEXPECTED = "unexpected";
    public static final String ERROR_BAD_CREDENTIALS = "bad_credentials";
    protected static final String ERROR_BAD_CAPTCHA = "bad_captcha";
    protected static final String ERROR_OBJ_NOT_FOUND = "obj_not_found";
    protected static final String ERROR_PATH_NOT_FOUND = "path_not_found";
    protected static final String ERROR_VERSION_MISMATCHED = "version_mismatched";
    protected static final String ERROR_UNEXPECTED_USERNAME = "unexpected_username";
    protected static final String ERROR_DISABLED_USER = "disabled_user";
    protected static final String ERROR_NOT_AUTHENTICATED = "not_authenticated";

    private final String code;
    private final HttpStatus httpStatus;

    public GeneralException(String code, HttpStatus httpStatus) {
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
