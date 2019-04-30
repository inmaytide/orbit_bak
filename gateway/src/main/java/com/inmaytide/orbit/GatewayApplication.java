package com.inmaytide.orbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customeRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authorization", ps -> ps.path("/auth/**").filters(f -> f.rewritePath("/auth", "/oauth")).uri("lb://authorization"))
                .route("base", ps -> ps.path("/base/**").filters(f -> f.rewritePath("/base/(?<segment>.*)", "/${segment}")).uri("lb://base"))
                .build();
    }
}
