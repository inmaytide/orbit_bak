package com.inmaytide.orbit.auth.filter;

import com.inmaytide.orbit.auth.token.FormAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class FormAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

    public FormAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String captcha = obtainCaptcha(request);
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        FormAuthenticationToken token = new FormAuthenticationToken(username, password, captcha);
        setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return obtainParameter(getPasswordParameter(), request);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return obtainParameter(getUsernameParameter(), request);
    }

    protected String obtainCaptcha(HttpServletRequest request) {
        return obtainParameter(getCaptchaParameter(), request);
    }

    protected String obtainParameter(String name, HttpServletRequest request) {
        return Objects.toString(request.getParameter(name), "");
    }

    public String getCaptchaParameter() {
        return captchaParameter;
    }

}
