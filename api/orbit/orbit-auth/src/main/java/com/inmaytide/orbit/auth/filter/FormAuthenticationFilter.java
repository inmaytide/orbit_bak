package com.inmaytide.orbit.auth.filter;

import com.inmaytide.orbit.auth.exception.IncorrectCaptchaException;
import com.inmaytide.orbit.auth.provider.CaptchaProvider;
import com.inmaytide.orbit.auth.token.FormAuthenticatedToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";
    public static final String SPRING_SECURITY_FORM_CAPTCHA_CACHE_NAME = "v";
    private final CaptchaProvider captchaProvider;
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
    private String captchaCacheNameParameter = SPRING_SECURITY_FORM_CAPTCHA_CACHE_NAME;

    public FormAuthenticationFilter(CaptchaProvider captchaProvider, AuthenticationManager authenticationManager) {
        super();
        Assert.notNull(captchaProvider, "CaptchaProvider must be not null");
        this.captchaProvider = captchaProvider;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String captcha = obtainCaptcha(request);
        String captchaCacheName = obtainCaptchaCacheName(request);
        captchaProvider.validation(captcha, captchaCacheName, IncorrectCaptchaException::new);


        String username = obtainUsername(request);
        String password = obtainPassword(request);

        username = StringUtils.isEmpty(username) ? "" : username.trim();
        password = StringUtils.isEmpty(password) ? "" : password;


        FormAuthenticatedToken token = new FormAuthenticatedToken(username, password, captcha, captchaCacheName);

        setDetails(request, token);

        return this.getAuthenticationManager().authenticate(token);
    }


    private String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(captchaParameter);
    }

    private String obtainCaptchaCacheName(HttpServletRequest request) {
        return request.getParameter(captchaCacheNameParameter);
    }

    public String getCaptchaParameter() {
        return captchaParameter;
    }

    public String getCaptchaCacheNameParameter() {
        return captchaCacheNameParameter;
    }
}
