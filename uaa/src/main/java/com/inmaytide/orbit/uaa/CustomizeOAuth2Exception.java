package com.inmaytide.orbit.uaa;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class CustomizeOAuth2Exception extends OAuth2Exception {
    public CustomizeOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomizeOAuth2Exception(String msg) {
        super(msg);
    }
}
