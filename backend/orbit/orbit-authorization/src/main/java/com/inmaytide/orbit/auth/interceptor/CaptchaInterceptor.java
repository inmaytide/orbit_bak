package com.inmaytide.orbit.auth.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.auth.client.CaptchaClient;
import com.inmaytide.orbit.auth.exception.BadCaptchaException;
import com.inmaytide.orbit.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.*;

/**
 * @author Moss
 * @since November 18, 2017
 */
public class CaptchaInterceptor implements WebRequestInterceptor {

    private CaptchaClient client;

    public CaptchaInterceptor(CaptchaClient client) {
        this.client = client;
    }

    private String checkCaptcha(String captcha) {
        return StringUtils.isBlank(captcha) ? "-1" : captcha;
    }


    @Override
    public void preHandle(@NonNull WebRequest request) {
        String value = request.getHeader(Constants.HEADER_NAME_CAPTCHA_NAME);
        if (StringUtils.isBlank(value)) {
            throw new BadCaptchaException();
        }

        ObjectNode response = client.validate(value, checkCaptcha(request.getParameter("captcha")));
        if (response == null) {
            throw new BadCaptchaException();
        }

        JsonNode isValid = response.get("isValid");
        if (isValid.isNull() || !isValid.asBoolean()) {
            throw new BadCaptchaException();
        }
    }

    @Override
    public void postHandle(@NonNull WebRequest request, @Nullable ModelMap model) {

    }

    @Override
    public void afterCompletion(@NonNull WebRequest request, @Nullable Exception ex) {

    }
}
