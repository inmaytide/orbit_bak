package com.inmaytide.orbit.auth.provider;

import com.inmaytide.orbit.auth.exception.BlankAccountException;
import com.inmaytide.orbit.auth.exception.IncorrectCaptchaException;
import com.inmaytide.orbit.auth.exception.UnknownAccountException;
import com.inmaytide.orbit.auth.service.UserService;
import com.inmaytide.orbit.auth.token.AuthenticatedToken;
import com.inmaytide.orbit.auth.token.FormAuthenticationToken;
import com.inmaytide.orbit.domain.sys.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

public class FormAuthenticationProvider implements AuthenticationProvider {

    private static final int DEFAULT_PASSWORD_ENCODER_STRENGTH = 10;

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaProvider captchaProvider;

    private PasswordEncoder passwordEncoder;


    public FormAuthenticationProvider() {
        setPasswordEncoder(new BCryptPasswordEncoder(DEFAULT_PASSWORD_ENCODER_STRENGTH));
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Objects.equals(cls, FormAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FormAuthenticationToken token = (FormAuthenticationToken) authentication;
        captchaProvider.validation(token.getCaptcha(), token.getCaptchaCacheName(), () -> new IncorrectCaptchaException(""));

        String username = Objects.toString(token.getPrincipal(), null);
        String password = Objects.toString(authentication.getCredentials(), null);

        User user = userService.getByUsername(verifyUsername(username))
                .map(value -> this.verifyPassword(value, password))
                .map(this::isLocked)
                .orElseThrow(() -> new UnknownAccountException(username));

        return new AuthenticatedToken(user, user.getPassword(), null);
    }

    private String verifyUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BlankAccountException("");
        }
        return username;
    }

    private User isLocked(User user) {
        if (user.isLocked()) {
            throw new LockedException("User [" + user.getUsername() + "] is locked");
        }
        return user;
    }

    private User verifyPassword(User user, String password) {
        if (!getPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return user;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


}
