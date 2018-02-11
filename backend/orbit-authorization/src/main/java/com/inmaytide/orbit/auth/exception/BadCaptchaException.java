package com.inmaytide.orbit.auth.exception;

/**
 * Throw when captcha is null, empty and mistake
 *
 * @author Moss
 * @since November 26, 2017
 * @see com.inmaytide.orbit.auth.interceptor.CaptchaInterceptor
 */
public class BadCaptchaException extends RuntimeException {

    private static final long serialVersionUID = 4371999368295695321L;

    public BadCaptchaException() {
        super();
    }

}
