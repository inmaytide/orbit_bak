package com.inmaytide.orbit.exception.handler;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

public class ResponseError implements Serializable {

    private Integer code;

    private String message;

    public static ResponseError of(HttpStatus status, String message) {
        Objects.requireNonNull(status);
        return new ResponseError(status, message);
    }

    private ResponseError(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
