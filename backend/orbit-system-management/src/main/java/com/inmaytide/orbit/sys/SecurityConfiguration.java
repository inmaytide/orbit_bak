package com.inmaytide.orbit.sys;

import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.commons.security.SecurityConfigurerAdapter;
import com.inmaytide.orbit.util.InsideTokenMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.match;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.notMatch;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration extends SecurityConfigurerAdapter {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;


    @Override
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling()
                .authenticationEntryPoint(exceptionHandler::handle)
                .and()
                .addFilterAt(jwtFilter(exceptionHandler), SecurityWebFiltersOrder.AUTHENTICATION)
                /*
                 * Replace the default exception translation filter
                 * Spring 提供的方式无法去掉默认ExceptionTranslationWebFilter（Servlet版本的可以通过exceptionHandling做到，
                 * webflux版本未提供修改AccessDeniedHandler的方法），
                 * 只是在默认的Filter之前添加了
                 * 一个自定义的filter, 但是这个自定义Filter放在默认的前面无效，必须放到后面，所以下面的Filter Order
                 * 传入的是ExceptionTranslationWebFilter后一个的顺序，也就是放在AuthorizationWebFilter之前
                 */
                .addFilterAt(exceptionTranslationWebFilter(exceptionHandler), SecurityWebFiltersOrder.AUTHORIZATION)
                .csrf().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(HttpMethod.POST, "/sys/permissions").hasAuthority("permission:add")
                .matchers(exchange -> new InsideTokenMatcher(exchange).match() ? match() : notMatch()).permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}
