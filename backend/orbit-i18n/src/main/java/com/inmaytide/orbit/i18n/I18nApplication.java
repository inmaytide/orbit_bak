package com.inmaytide.orbit.i18n;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Moss
 * @since September 22, 2017
 */
@SpringBootApplication
@EnableWebFlux
@EnableDiscoveryClient
public class I18nApplication {

    public static void main(String... args) {
        SpringApplication.run(I18nApplication.class, args);
    }

    @Bean
    public RouterFunction<?> routers(I18nResourceProvider provider) {
        return route(GET("/lang/{lang}"), provider::lang)
                .andOther(route(RequestPredicates.all(), GlobalExceptionHandler::notFound));
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction<?> routers) {
        return WebHttpHandlerBuilder
                .webHandler(RouterFunctions.toWebHandler(routers))
                .exceptionHandler(new GlobalExceptionHandler())
                .build();
    }

}
