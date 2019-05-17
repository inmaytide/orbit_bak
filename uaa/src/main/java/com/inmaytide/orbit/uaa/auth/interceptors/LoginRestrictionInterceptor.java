package com.inmaytide.orbit.uaa.auth.interceptors;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginRestrictionInterceptor implements HandlerInterceptor {

    private static final String P_NAME_USERNAME = "username";

    private final StringRedisTemplate redisTemplate;

    public LoginRestrictionInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().endsWith("/oauth/token")) {
            return true;
        }



        // redisTemplate.hasKey(request.getParameter("P_NAME_USERNAME"));


        return false;
    }
}
