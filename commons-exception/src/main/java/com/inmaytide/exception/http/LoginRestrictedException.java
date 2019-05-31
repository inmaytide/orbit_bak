package com.inmaytide.orbit.commons.exception.http;

import org.springframework.http.HttpStatus;

public class LoginRestrictedException extends HttpResponseException {

    private static final String DEFAULT_CODE = "err_login_restricted";

    public LoginRestrictedException(String restrictedTime) {
        super(HttpStatus.FORBIDDEN, DEFAULT_CODE + "_" + restrictedTime);
    }

}
