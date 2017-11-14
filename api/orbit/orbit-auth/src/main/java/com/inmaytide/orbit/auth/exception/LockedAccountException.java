package com.inmaytide.orbit.auth.exception;

public class LockedAccountException extends RuntimeException {

    private static final long serialVersionUID = 7406088816392116950L;

    public LockedAccountException() {

    }

    public LockedAccountException(String message) {
        super(message);
    }

}
