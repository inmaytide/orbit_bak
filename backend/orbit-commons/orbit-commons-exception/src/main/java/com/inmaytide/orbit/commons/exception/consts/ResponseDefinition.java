package com.inmaytide.orbit.commons.exception.consts;

import org.springframework.http.HttpStatus;

public enum ResponseDefinition {

    UNEXPECTED("unexpected", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_CREDENTIALS("bad_credentials", HttpStatus.UNAUTHORIZED),
    BAD_CAPTCHA("bad_captcha", HttpStatus.UNAUTHORIZED),
    UNEXPECTED_USERNAME("unexpected_username", HttpStatus.UNAUTHORIZED),
    NOT_AUTHENTICATED("not_authenticated", HttpStatus.UNAUTHORIZED),
    DISABLED_USER("disabled_user", HttpStatus.FORBIDDEN),
    PERMISSION_DENIED("permission_denied", HttpStatus.FORBIDDEN),
    EXPIRED_TOKEN("expired_token", HttpStatus.FORBIDDEN),
    OBJ_NOT_FOUND("obj_not_found", HttpStatus.NOT_FOUND),
    OBJ_INVALID("obj_invalid", HttpStatus.BAD_REQUEST),
    PATH_NOT_FOUND("path_not_found", HttpStatus.NOT_FOUND),
    VERSION_MISMATCHED("version_mismatched", HttpStatus.CONFLICT);

    private String code;
    private HttpStatus status;

    ResponseDefinition(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }


    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
