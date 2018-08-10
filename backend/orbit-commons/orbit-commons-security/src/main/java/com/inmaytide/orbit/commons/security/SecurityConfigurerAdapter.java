package com.inmaytide.orbit.commons.security;

import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.commons.security.authentication.JwtAuthenticationConverter;
import com.inmaytide.orbit.commons.security.authentication.JwtAuthenticationManager;
import com.inmaytide.orbit.commons.security.matcher.InsideTokenMatcher;
import com.inmaytide.orbit.commons.util.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import static com.inmaytide.orbit.commons.Constants.SIGNING_KEY;

public abstract class SecurityConfigurerAdapter implements InitializingBean {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    @Bean
    public JwtAccessTokenConverter converter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(converter());
    }


    protected ServerHttpSecurity httpSecurityConfiguer(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint(getExceptionHandler()::handle)
                .accessDeniedHandler(getExceptionHandler()::handle)
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAt(jwtFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .matchers(exchange -> new InsideTokenMatcher(exchange).match()).permitAll()
                .and();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return httpSecurityConfiguer(http).build();
    }

    protected AuthenticationWebFilter jwtFilter() {
        Assert.nonNull(exceptionHandler, "exceptionHandler must be not null");
        AuthenticationWebFilter filter = new AuthenticationWebFilter(new JwtAuthenticationManager());
        filter.setAuthenticationConverter(new JwtAuthenticationConverter(tokenStore()));
        filter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(getExceptionHandler()::handle));
        return filter;
    }


    protected GlobalExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.nonNull(exceptionHandler, "Exception handler for security must not be null");
    }
}
