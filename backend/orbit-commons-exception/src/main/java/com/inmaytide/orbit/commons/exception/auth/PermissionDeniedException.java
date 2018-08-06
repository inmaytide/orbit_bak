package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;

/**
 * @author Moss
 * @since August 04, 2018
 */
public class PermissionDeniedException extends ResponseException {
    private static final long serialVersionUID = 6758390661200583145L;

    public PermissionDeniedException() {
        super(ResponseDefinition.PERMISSION_DENIED);
    }
}
