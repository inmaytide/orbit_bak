package com.inmaytide.orbit.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Override the default web exception translator, add exception handling for {@link BadCaptchaException}
 *
 * @author Moss
 * @see BadCaptchaException
 * @since November 26, 2017
 */
public class DefaultWebResponseExceptionTranslator extends org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator {

    private static final Logger log = LoggerFactory.getLogger(DefaultWebResponseExceptionTranslator.class);

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        log.error("Exception handing......", e);
        if (e instanceof BadCaptchaException) {
            OAuth2Exception exception = new OAuth2Exception("Bad captcha", e);
            return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
        }
        return super.translate(e);
    }
}
