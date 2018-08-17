package com.inmaytide.orbit.dictionary;

import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.commons.security.SecurityConfigurerAdapter;
import com.inmaytide.orbit.dictionary.handler.DataDictionaryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

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
@EnableFeignClients
@SpringBootApplication
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class DataDictionaryApplication extends SecurityConfigurerAdapter {


    private final GlobalExceptionHandler exceptionHandler;

    @Autowired
    public DataDictionaryApplication() {
        this.exceptionHandler = new GlobalExceptionHandler();
    }

    public static void main(String... args) {
        SpringApplication.run(DataDictionaryApplication.class, args);
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
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return httpSecurityConfiguer(http)
                .authorizeExchange().anyExchange().authenticated()
                .and().build();
    }
}
