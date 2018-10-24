package com.inmaytide.orbit.auth.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.auth.client.CaptchaClient;
import com.inmaytide.orbit.commons.exception.auth.BadCaptchaException;
import com.inmaytide.orbit.commons.util.Assert;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moss
 * @since November 18, 2017
 */
public class CaptchaInterceptor implements HandlerInterceptor {

    private static final String PARAMETER_NAME_CAPTCHA = "captcha";

    public static final String HEADER_NAME_CAPTCHA_NAME = "x-captcha-name";

    private CaptchaClient client;

    public CaptchaInterceptor(CaptchaClient client) {
        this.client = client;
    }

    private String getCaptchaValue(HttpServletRequest request) {
        String value = request.getParameter(PARAMETER_NAME_CAPTCHA);
        Assert.hasText(value, BadCaptchaException::new);
        return value;
    }

    private String getCaptchaName(HttpServletRequest request) {
        String name = request.getHeader(HEADER_NAME_CAPTCHA_NAME);
        Assert.hasText(name, BadCaptchaException::new);
        return name;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().endsWith(".well-known/jwks.json")) {
            return true;
        }

        ObjectNode res = client.validate(getCaptchaName(request), getCaptchaValue(request));
        Assert.nonNull(response, BadCaptchaException::new);
        JsonNode isValid = res.get("isValid");
        Assert.isTrue(!isValid.isNull() && isValid.asBoolean(), BadCaptchaException::new);
        return true;
    }
}
