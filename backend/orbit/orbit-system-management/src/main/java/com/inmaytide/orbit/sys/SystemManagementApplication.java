package com.inmaytide.orbit.sys;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.sys.domain.User;
import com.inmaytide.orbit.sys.filter.VisitorResolver;
import com.inmaytide.orbit.sys.service.UserService;
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

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
public class SystemManagementApplication extends WebFluxConfigurationSupport {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> VisitorResolver.currentVisitor().map(User::getId);
    }

    @Bean
    public VisitorResolver visitorResolver(final UserService userService) {
        return new VisitorResolver(username ->
            userService.getByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("Unauthorized access, please access the system through normal channels."))
        );
    }

    @Bean
    @Order(-1)
    public GlobalExceptionHandler errorHandler() {
        return new GlobalExceptionHandler(true);
    }

    public static void main(String... args) {
        SpringApplication.run(SystemManagementApplication.class, args);
    }

}
