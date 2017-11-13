package com.inmaytide.orbit.auth;

import com.inmaytide.orbit.auth.handler.LoginHandler;
import com.inmaytide.orbit.auth.provider.FormAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
public class AuthApplication {

    public static void main(String... args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Autowired
    private LoginHandler loginHandler;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FormAuthenticationProvider customizeAuthenticationProvider() {
        return new FormAuthenticationProvider();
    }

    @Bean
    public RouterFunction<?> routers() {
        RouterFunction<?> routers = route(POST("/login"), loginHandler::login);
        return routers;
    }



}
