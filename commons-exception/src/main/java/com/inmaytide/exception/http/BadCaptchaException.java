package com.inmaytide.orbit.commons.exception.http;

import org.springframework.http.HttpStatus;

public class BadCaptchaException extends HttpResponseException {

    private static final String DEFAULT_CODE = "err_bad_captcha";

    public BadCaptchaException() {
        super(HttpStatus.FORBIDDEN, DEFAULT_CODE);
    }

}
