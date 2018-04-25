package com.inmaytide.orbit.commons.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class ThreadLocalAuthenticationHolder {

    private static final Logger log = LoggerFactory.getLogger(ThreadLocalAuthenticationHolder.class);

    private static final ThreadLocal<Authentication> AUTHENTICATION_THREAD_LOCAL = new ThreadLocal<>();

    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(AUTHENTICATION_THREAD_LOCAL.get());
    }

    static void setAuthentication(Authentication authentication) {
        try {
            AUTHENTICATION_THREAD_LOCAL.set(authentication);
        } catch (Exception e) {
            resetAuthentication();
            log.warn("Unable to store authentication on local thread", e);
        }
    }

    static void resetAuthentication() {
        AUTHENTICATION_THREAD_LOCAL.remove();
    }

}
