package com.inmaytide.orbit.uaa.utils;

import com.inmaytide.exception.http.BadCredentialsException;
import com.inmaytide.orbit.uaa.domain.User;
import com.inmaytide.orbit.uaa.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Objects;
import java.util.Optional;

/**
 * @author luomiao
 * @since 2019/09/10
 */
public class AuthenticatedHelper {

    public static Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        String name = Objects.toString(token.getTokenAttributes().get("user_name"), "");
        if (StringUtils.isBlank(name)) {
            throw new BadCredentialsException();
        }
        return ContextHolder.getBean(UserService.class).getByUsername(name);
    }

}
