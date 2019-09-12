package com.inmaytide.orbit;

import com.inmaytide.exception.http.handler.reactive.DefaultExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.WebExceptionHandler;

@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customeRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("uaa", ps -> ps.path("/uaa/**").filters(f -> f.rewritePath("/uaa/(?<segment>.*)", "/${segment}")).uri("lb://uaa"))
                .route("captcha", ps -> ps.path("/captcha/**").uri("lb://captcha"))
                .route("id", ps -> ps.path("/api/id/**").uri("lb://core"))
                .build();
    }

    @Bean
    @Order(-1)
    public WebExceptionHandler exceptionHandler() {
        return new DefaultExceptionHandler();
    }

}
