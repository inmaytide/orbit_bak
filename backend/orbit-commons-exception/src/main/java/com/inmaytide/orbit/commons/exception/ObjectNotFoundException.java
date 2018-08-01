package com.inmaytide.orbit.commons.exception;

public class ObjectNotFoundException extends AbstractException{
    public ObjectNotFoundException() {
        super(ExceptionCode.OBJECT_NOT_FOUND);
    }
}
