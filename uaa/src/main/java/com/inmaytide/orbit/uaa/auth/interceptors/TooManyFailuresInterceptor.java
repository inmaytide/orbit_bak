package com.inmaytide.orbit.uaa.auth.interceptors;

import com.inmaytide.exception.http.LoginRefusedException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TooManyFailuresInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().endsWith("/oauth/token")) {
            return true;
        }

        if (LoginFailuresHelper.refused(request)) {
            throw new LoginRefusedException();
        }

        return true;
    }


}
