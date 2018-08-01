package com.inmaytide.orbit.commons.exception;

public abstract class AbstractException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public AbstractException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }


    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
