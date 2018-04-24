package com.inmaytide.orbit.security.commons;

import com.inmaytide.orbit.constant.Constants;
import com.inmaytide.orbit.util.Assert;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authorization.ExceptionTranslationWebFilter;

public abstract class SecurityConfigurerAdapter {

    @Bean
    public JwtAccessTokenConverter converter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(Constants.SIGNING_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(converter());
    }

    @Bean
    public abstract SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http);

    protected AuthenticationWebFilter jwtFilter(ErrorWebExceptionHandler exceptionHandler) {
        Assert.nonNull(exceptionHandler, "exceptionHandler must be not null");
        AuthenticationWebFilter filter = new AuthenticationWebFilter(new JwtAuthenticationManager());
        filter.setAuthenticationConverter(new JwtAuthenticationConverter(tokenStore()));
        filter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(exceptionHandler::handle));
        return filter;
    }

    protected ExceptionTranslationWebFilter exceptionTranslationWebFilter(ErrorWebExceptionHandler exceptionHandler) {
        Assert.nonNull(exceptionHandler, "exceptionHandler must be not null");
        ExceptionTranslationWebFilter exceptionTranslationWebFilter = new ExceptionTranslationWebFilter();
        exceptionTranslationWebFilter.setAuthenticationEntryPoint(exceptionHandler::handle);
        exceptionTranslationWebFilter.setAccessDeniedHandler(exceptionHandler::handle);
        return exceptionTranslationWebFilter;
    }


}
