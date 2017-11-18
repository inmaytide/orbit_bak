package com.inmaytide.orbit.auth.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @author Moss
 * @since November 18, 2017
 */
public class CaptchaInterceptor implements WebRequestInterceptor {


    @Override
    public void preHandle(WebRequest request) throws Exception {
        String captcha = request.getParameter("captcha");
        System.out.println(captcha);
    }

    @Override
    public void postHandle(WebRequest request, @Nullable ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, @Nullable Exception ex) throws Exception {

    }
}
