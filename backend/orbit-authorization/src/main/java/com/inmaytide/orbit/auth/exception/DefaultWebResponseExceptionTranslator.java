package com.inmaytide.orbit.auth.exception;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Override the default web exception translator, add exception handling for {@link GeneralException}
 *
 * @author Moss
 * @see GeneralException
 * @since November 26, 2017
 */
public class DefaultWebResponseExceptionTranslator extends org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        if (e.getCause() instanceof GeneralException) {
            GeneralException cause = (GeneralException) e.getCause();
            OAuth2Exception exception = new OAuth2Exception(cause.getCode(), e);
            return new ResponseEntity<>(exception, cause.getHttpStatus());
        }
        if (e instanceof InvalidGrantException && "Bad credentials".equals(e.getMessage())) {
            OAuth2Exception exception = new OAuth2Exception(GeneralException.ERROR_BAD_CREDENTIALS, e);
            return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
        }

        return super.translate(e);
    }
}
