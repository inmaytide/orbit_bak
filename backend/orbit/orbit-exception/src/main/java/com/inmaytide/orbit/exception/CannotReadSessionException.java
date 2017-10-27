package com.inmaytide.orbit.exception;

public class CannotReadSessionException extends RuntimeException {

    public CannotReadSessionException() {
        super();
    }

    public CannotReadSessionException(String message) {
        super(message);
    }

    public CannotReadSessionException(Throwable e) {
        super(e);
    }

}
