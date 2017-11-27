package com.inmaytide.orbit.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class DefaultWebResponseExceptionTranslator extends org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        if (e instanceof BadCaptchaException) {
            OAuth2Exception exception = new OAuth2Exception("Bad captcha", e);
            return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
        }

        return super.translate(e);
    }
}
