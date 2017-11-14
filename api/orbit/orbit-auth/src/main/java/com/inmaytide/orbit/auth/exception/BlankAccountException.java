package com.inmaytide.orbit.auth.exception;

public class BlankAccountException extends RuntimeException {

    private static final long serialVersionUID = -5651030624023600550L;

    public BlankAccountException() {
    }

    public BlankAccountException(String message) {
        super(message);
    }
}
