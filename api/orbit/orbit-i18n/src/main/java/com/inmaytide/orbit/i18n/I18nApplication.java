package com.inmaytide.orbit.i18n;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import javax.annotation.Resource;

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

    @Resource
    private MessageSource messageSource;

    @Value("#{ @environment['orbit.i18n.resource-cache'] ?: true }")
    private boolean isCache;

    @Value("#{ @environment['spring.messages.basename'] ?: 'messages' }")
    private String basename;

    public static void main(String... args) {
        SpringApplication.run(I18nApplication.class, args);
    }

    @Bean
    public I18nMessages i18nMessages() {
        return new I18nMessages(messageSource);
    }

    @Bean
    public I18nResourceHolder i18nResourceHolder() {
        return new I18nResourceHolder(basename, isCache);
    }

    @Bean
    public I18nResourceProvider i18nResourceProvider() {
        return new I18nResourceProvider();
    }

    @Bean
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public RouterFunction<?> routers(I18nResourceProvider provider, GlobalExceptionHandler exceptionHandler) {
        return route(GET("/lang/{lang}"), provider::lang)
                .andOther(route(RequestPredicates.all(), exceptionHandler::notFound));
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction<?> routers, GlobalExceptionHandler exceptionHandler) {
        return WebHttpHandlerBuilder
                .webHandler(RouterFunctions.toWebHandler(routers, HandlerStrategies.builder().build()))
                .exceptionHandler(exceptionHandler)
                .build();
    }

}
