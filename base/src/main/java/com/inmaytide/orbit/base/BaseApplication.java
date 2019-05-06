package com.inmaytide.orbit.base;

import com.inmaytide.orbit.base.auth.AnonymousMatcher;
import com.inmaytide.orbit.commons.exception.handler.DefaultExceptionHandler;
import com.inmaytide.orbit.id.IdGenerator;
import com.inmaytide.orbit.id.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebExceptionHandler;

@SpringBootApplication
@EnableEurekaClient
@EnableWebFlux
public class BaseApplication implements WebFluxConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

    @EnableWebFluxSecurity
    @EnableReactiveMethodSecurity
    class SecurityConfiguration {
        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, WebExceptionHandler exceptionHandler) {
            return http.exceptionHandling()
                    .authenticationEntryPoint(exceptionHandler::handle)
                    .accessDeniedHandler(exceptionHandler::handle)
                    .and()
                    .csrf().disable()
                    .authorizeExchange()
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()
                    .matchers(exchange -> new AnonymousMatcher(exchange).match()).permitAll()
                    .anyExchange().authenticated()
                    .and().oauth2ResourceServer().jwt().and()
                    .and().build();

        }


    }

    @Bean
    @Order(-1)
    public DefaultExceptionHandler exceptionHandler() {
        return new DefaultExceptionHandler();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(workerId, dataCenterId);
    }

    @Value("${application.id.worker-id:1}")
    private Integer workerId;

    @Value("${application.id.data-center-id:1}")
    private Integer dataCenterId;

}
