package com.inmaytide.orbit.auth.exception;

public class IncorrectCredentialsException extends RuntimeException {

    private static final long serialVersionUID = 7631956957753749681L;

    public IncorrectCredentialsException() {
    }

    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
