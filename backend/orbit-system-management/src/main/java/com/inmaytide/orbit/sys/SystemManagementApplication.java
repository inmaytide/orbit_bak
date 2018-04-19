package com.inmaytide.orbit.sys;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.filter.visitor.DefaultVisitorResolver;
import com.inmaytide.orbit.sys.service.UserService;
import com.inmaytide.orbit.util.JsonUtils;
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

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> DefaultVisitorResolver.currentVisitor()
                .map(node -> node.get("id").asLong())
                .or(Optional::empty);
    }

    @Bean
    public DefaultVisitorResolver visitorResolver(final UserService userService) {
        return new DefaultVisitorResolver(username ->
            userService.getByUsername(username)
                    .map(JsonUtils::serialize)
                    .orElseThrow(() -> new IllegalStateException("Unauthorized access, please access the system through normal channels."))
        );
    }

    @Bean
    @Order(-1)
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler(true);
    }

    public static void main(String... args) {
        SpringApplication.run(SystemManagementApplication.class, args);
    }

}
