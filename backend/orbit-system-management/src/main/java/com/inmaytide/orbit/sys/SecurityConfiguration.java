package com.inmaytide.orbit.sys;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.security.commons.SecurityConfigurerAdapter;
import com.inmaytide.orbit.util.AccessTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.match;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.notMatch;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration extends SecurityConfigurerAdapter {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint(exceptionHandler::handle)
                .and()
                .addFilterAt(oauth2Filter(exceptionHandler), SecurityWebFiltersOrder.AUTHENTICATION)
                /*
                 * Replace the default exception translation filter
                 * Spring 提供的方式无法去掉默认ExceptionTranslationWebFilter（Servlet版本的可以通过exceptionHandling做到，
                 * webflux版本提供修改AccessDeniedHandler的方法），
                 * 只是在默认的Filter之前添加了
                 * 一个自定义的filter, 但是这个自定义Filter放在默认的前面无效，必须放到后面，所以下面的Filter Order
                 * 传入的是ExceptionTranslationWebFilter后一个的顺序，也就是放在AuthorizationWebFilter之前
                 */
                .addFilterAt(exceptionTranslationWebFilter(exceptionHandler), SecurityWebFiltersOrder.AUTHORIZATION)
                .csrf().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(HttpMethod.GET, "/sys/permissions").hasAuthority("permissions")
                .matchers(exchange -> AccessTokenUtils.matchInnerToken(exchange) ? match() : notMatch()).permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}
