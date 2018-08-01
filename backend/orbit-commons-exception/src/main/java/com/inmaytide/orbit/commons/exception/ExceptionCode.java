package com.inmaytide.orbit.commons.exception;

public enum ExceptionCode {

    OBJECT_NOT_FOUND("object_not_found"),
    PATH_NOT_FOUND("path_not_found"),
    VERSION_MISMATCHED("version_mismatched");

    private String text;

    ExceptionCode(String text) {
        this.text = text;
    }

}
