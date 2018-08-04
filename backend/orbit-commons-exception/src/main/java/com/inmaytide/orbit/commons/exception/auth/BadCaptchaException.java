package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class BadCaptchaException extends GeneralException {

    private static final long serialVersionUID = 8861478851558676L;

    public BadCaptchaException() {
        super(ERROR_BAD_CAPTCHA, HttpStatus.BAD_REQUEST);
    }

}
