package com.inmaytide.orbit.sys;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.security.commons.ThreadLocalAuthenticationHolder;
import com.inmaytide.orbit.sys.domain.User;
import com.inmaytide.orbit.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;

import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
public class SystemManagementApplication extends WebFluxConfigurationSupport {

    @Autowired
    private UserService userService;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> ThreadLocalAuthenticationHolder.getAuthentication()
                .flatMap(authentication -> userService.getByUsername(authentication.getName()))
                .map(User::getId)
                .or(Optional::empty);
    }

    @Bean
    @Order(-1)
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }

    public static void main(String... args) {
        SpringApplication.run(SystemManagementApplication.class, args);
    }

}
