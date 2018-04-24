package com.inmaytide.orbit.dictionary;

import com.inmaytide.orbit.dictionary.client.UserClient;
import com.inmaytide.orbit.dictionary.handler.DataDictionaryHandler;
import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.security.commons.SecurityConfigurerAdapter;
import com.inmaytide.orbit.security.commons.ThreadLocalAuthenticationHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import java.security.Principal;
import java.util.Optional;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Moss
 * @since November 28, 2017
 */
@EnableWebFlux
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories
@EnableFeignClients
@SpringBootApplication
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class DataDictionaryApplication extends SecurityConfigurerAdapter {

    private final UserClient userClient;

    private final GlobalExceptionHandler exceptionHandler;

    @Autowired
    public DataDictionaryApplication(UserClient userClient) {
        this.userClient = userClient;
        this.exceptionHandler = new GlobalExceptionHandler();
    }

    public static void main(String... args) {
        SpringApplication.run(DataDictionaryApplication.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> ThreadLocalAuthenticationHolder.getAuthentication()
                .map(Principal::getName)
                .flatMap(userClient::getUserByUsername)
                .map(o -> o.get("id").asLong())
                .or(Optional::empty);
    }

    @Bean
    public RouterFunction<?> routers(DataDictionaryHandler handler) {
        RouterFunction<?> routers = route(GET("/"), handler::list)
                .and(route(POST("/"), handler::add))
                .andOther(route(RequestPredicates.all(), GlobalExceptionHandler::notFound));
        return nest(RequestPredicates.path("/dictionaries"), routers);
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction<?> routers, WebFilter springSecurityFilterChain) {
        return WebHttpHandlerBuilder.webHandler(RouterFunctions.toWebHandler(routers))
                .exceptionHandler(exceptionHandler)
                .filter(springSecurityFilterChain)
                .build();
    }

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
                .pathMatchers(HttpMethod.POST, "/dictionaries").hasAuthority("dictionaries:add")
                .anyExchange().authenticated()
                .and().build();
    }
}
