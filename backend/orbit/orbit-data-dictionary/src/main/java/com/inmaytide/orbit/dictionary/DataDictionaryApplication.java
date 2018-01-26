package com.inmaytide.orbit.dictionary;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.dictionary.client.UserClient;
import com.inmaytide.orbit.dictionary.handler.DataDictionaryHandler;
import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.filter.visitor.DefaultVisitorResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

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
public class DataDictionaryApplication {

    public static void main(String... args) {
        SpringApplication.run(DataDictionaryApplication.class, args);
    }

    @Autowired
    private UserClient userClient;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> DefaultVisitorResolver.currentVisitor()
                .map(node -> node.get("id").asLong())
                .or(Optional::empty);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DefaultVisitorResolver visitorResolver() {
        return new DefaultVisitorResolver(username -> userClient.getUserByUsername(username).map(ObjectNode::toString).orElse(null));
    }

    @Bean
    public RouterFunction<?> routers(DataDictionaryHandler handler) {
        RouterFunction<?> routers = route(GET("/"), handler::list)
                .and(route(POST("/"), handler::add));
        return nest(RequestPredicates.path("/dictionaries"), routers)
                .andOther(route(RequestPredicates.all(), exceptionHandler()::notFound));
    }

    @Bean
    public GlobalExceptionHandler exceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        exceptionHandler.setShowStackTrace(true);
        return exceptionHandler;
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction<?> routers, DefaultVisitorResolver visitorResolver) {
        return WebHttpHandlerBuilder.webHandler(RouterFunctions.toWebHandler(routers))
                .exceptionHandler(exceptionHandler())
                .filter(visitorResolver)
                .build();
    }

}
