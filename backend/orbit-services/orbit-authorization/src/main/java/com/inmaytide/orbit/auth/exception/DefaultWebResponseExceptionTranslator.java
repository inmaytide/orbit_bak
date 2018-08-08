package com.inmaytide.orbit.auth.exception;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Override the default web exception translator, add exception handling for {@link ResponseException}
 *
 * @author Moss
 * @see ResponseException
 * @since November 26, 2017
 */
public class DefaultWebResponseExceptionTranslator extends org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        if (e.getCause() instanceof ResponseException) {
            ResponseException cause = (ResponseException) e.getCause();
            OAuth2Exception exception = new OAuth2Exception(cause.getCode(), e);
            return new ResponseEntity<>(exception, cause.getHttpStatus());
        }
        if (e instanceof InvalidGrantException && "Bad credentials".equals(e.getMessage())) {
            OAuth2Exception exception = new OAuth2Exception(ResponseDefinition.BAD_CREDENTIALS.getCode(), e);
            return new ResponseEntity<>(exception, ResponseDefinition.BAD_CREDENTIALS.getStatus());
        }

        return super.translate(e);
    }
}
