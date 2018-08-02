package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class BadCaptchaException extends GeneralException {

    public BadCaptchaException() {
        super(ERROR_BAD_CAPTCHA, HttpStatus.BAD_REQUEST);
    }

}
