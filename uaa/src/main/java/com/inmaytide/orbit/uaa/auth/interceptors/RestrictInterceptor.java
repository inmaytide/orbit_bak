package com.inmaytide.orbit.uaa.auth.interceptors;

import com.inmaytide.orbit.uaa.utils.RestrictUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestrictInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().endsWith("/oauth/token")) {
            return true;
        }
        RestrictUtil.forbidden(request);
        return true;
    }
}
