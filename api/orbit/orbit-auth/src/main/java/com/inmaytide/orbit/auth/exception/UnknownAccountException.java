package com.inmaytide.orbit.auth.exception;

public class UnknownAccountException extends RuntimeException {

    private static final long serialVersionUID = -2735363315342733838L;

    public UnknownAccountException() {
    }

    public UnknownAccountException(String message) {
        super(message);
    }
}
