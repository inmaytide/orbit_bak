package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;

public class BadCaptchaException extends ResponseException {

    private static final long serialVersionUID = 8861478851558676L;

    public BadCaptchaException() {
        super(ResponseDefinition.BAD_CAPTCHA);
    }

}
