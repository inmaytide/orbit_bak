package com.inmaytide.orbit.dictionary;

import com.inmaytide.orbit.dictionary.handler.DataDictionaryHandler;
import com.inmaytide.orbit.dictionary.handler.VisitorResolver;
import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Moss
 * @since November 28, 2017
 */
@SpringBootApplication
@EnableWebFlux
@EnableJpaRepositories
public class DataDictionaryApplication {

    public static void main(String... args) {
        SpringApplication.run(DataDictionaryApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public VisitorResolver visitorResolver() {
        return new VisitorResolver();
    }

    @Bean
    public RouterFunction<?> routers(DataDictionaryHandler handler) {
        RouterFunction<?> routers = route(GET("/"), handler::list);
        return nest(RequestPredicates.path("/dictionaries"), routers);
    }

    @Bean
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction<?> routers) {
        return WebHttpHandlerBuilder.webHandler(RouterFunctions.toWebHandler(routers))
                .exceptionHandler(exceptionHandler())
                .filter(visitorResolver())
                .build();
    }

}
