package com.inmaytide.orbit.commons.exception;

public class ObjectNotFoundException extends DefaultRuntimeException {

    public ObjectNotFoundException() {
        super(404, "err_object_not_found");
    }

}
