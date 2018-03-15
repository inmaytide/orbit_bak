package com.inmaytide.orbit.auth.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.auth.client.CaptchaClient;
import com.inmaytide.orbit.auth.exception.BadCaptchaException;
import com.inmaytide.orbit.util.Assert;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.*;

import static com.inmaytide.orbit.constant.Constants.HEADER_NAME_CAPTCHA_NAME;

/**
 * @author Moss
 * @since November 18, 2017
 */
public class CaptchaInterceptor implements WebRequestInterceptor {

    private static final String PARAMETER_NAME_CAPTCHA = "captcha";

    private CaptchaClient client;

    public CaptchaInterceptor(CaptchaClient client) {
        this.client = client;
    }

    private String getCaptchaValue(WebRequest request) {
        String value = request.getParameter(PARAMETER_NAME_CAPTCHA);
        Assert.hasText(value, BadCaptchaException::new);
        return value;
    }

    private String getCaptchaName(WebRequest request) {
        String name = request.getHeader(HEADER_NAME_CAPTCHA_NAME);
        Assert.hasText(name, BadCaptchaException::new);
        return name;
    }

    @Override
    public void preHandle(@NonNull WebRequest request) {
        ObjectNode response = client.validate(getCaptchaName(request), getCaptchaValue(request));
        Assert.nonNull(response, BadCaptchaException::new);
        JsonNode isValid = response.get("isValid");
        Assert.isTrue(!isValid.isNull() && isValid.asBoolean(), BadCaptchaException::new);
    }

    @Override
    public void postHandle(@NonNull WebRequest request, @Nullable ModelMap model) {

    }

    @Override
    public void afterCompletion(@NonNull WebRequest request, @Nullable Exception ex) {

    }
}
