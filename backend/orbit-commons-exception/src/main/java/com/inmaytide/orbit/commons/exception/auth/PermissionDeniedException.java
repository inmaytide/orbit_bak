package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

/**
 * @author Moss
 * @since August 04, 2018
 */
public class PermissionDeniedException extends GeneralException {
    private static final long serialVersionUID = 6758390661200583145L;

    public PermissionDeniedException() {
        super(ERROR_PERMISSION_DENIED, HttpStatus.FORBIDDEN);
    }
}
