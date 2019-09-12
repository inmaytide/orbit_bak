package com.inmaytide.orbit.uaa.auth.interceptors;

import com.inmaytide.exception.http.BadCaptchaException;
import com.inmaytide.orbit.uaa.auth.service.CaptchaService;
import com.inmaytide.orbit.uaa.domain.dto.CaptchaValidate;
import com.inmaytide.orbit.uaa.utils.ContextHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaInterceptor implements HandlerInterceptor {

    private static final String P_NAME_CAPTCHA_NAME = "captcha_name";
    private static final String P_NAME_CAPTCHA = "captcha";

    private final CaptchaService service;

    public CaptchaInterceptor() {
        this.service = ContextHolder.getBean(CaptchaService.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().endsWith("/oauth/token") || !LoginFailuresHelper.neededCaptcha(request)) {
            return true;
        }

        String captchaName = request.getParameter(P_NAME_CAPTCHA_NAME);
        check(StringUtils.isNotBlank(captchaName));

        String captcha = request.getParameter(P_NAME_CAPTCHA);
        check(StringUtils.isNotBlank(captcha));

        CaptchaValidate valid = service.validate(captcha, captchaName);
        check(valid.isValid());

        return true;
    }

    private void check(boolean expression) {
        if (!expression) {
            throw new BadCaptchaException();
        }
    }
}
