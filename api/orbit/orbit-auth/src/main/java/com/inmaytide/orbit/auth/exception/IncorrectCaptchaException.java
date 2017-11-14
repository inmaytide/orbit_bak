package com.inmaytide.orbit.auth.exception;


public class IncorrectCaptchaException extends RuntimeException {

    private static final long serialVersionUID = 2917999752814351528L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

}
