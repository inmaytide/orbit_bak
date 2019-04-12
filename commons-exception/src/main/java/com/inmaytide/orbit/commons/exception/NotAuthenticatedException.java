package com.inmaytide.orbit.commons.exception;

public class NotAuthenticatedException extends DefaultRuntimeException {

    public NotAuthenticatedException() {
        super(401, "err_not_authenticated");
    }

}
