package com.inmaytide.orbit.commons.exception;

public class PathNotFoundException extends DefaultRuntimeException {

    public PathNotFoundException() {
        super(404, "err_path_not_found");
    }

}
