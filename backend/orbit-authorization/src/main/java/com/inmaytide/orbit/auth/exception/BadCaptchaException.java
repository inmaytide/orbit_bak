package com.inmaytide.orbit.auth.exception;

/**
 * Throws when captcha is null, blank or mistake
 *
 * @author Moss
 * @see com.inmaytide.orbit.auth.interceptor.CaptchaInterceptor
 * @since November 26, 2017
 */
public class BadCaptchaException extends RuntimeException {

    private static final long serialVersionUID = 4371999368295695321L;

    public BadCaptchaException() {
        super();
    }

}
