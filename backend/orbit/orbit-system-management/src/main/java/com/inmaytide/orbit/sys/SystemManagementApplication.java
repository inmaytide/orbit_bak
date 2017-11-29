package com.inmaytide.orbit.sys;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.sys.controller.ErrorHandler;
import com.inmaytide.orbit.sys.domain.User;
import com.inmaytide.orbit.sys.filter.VisitorResolver;
import com.inmaytide.orbit.sys.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

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
        return () -> VisitorResolver.currentVisitor().map(User::getId);
    }

    @Bean
    public VisitorResolver visitorResolver(UserService userService) {
        return new VisitorResolver(userService);
    }

    @Bean
    @Order(-1)
    public ErrorHandler errorHandler() {
        return new ErrorHandler();
    }

    public static void main(String... args) {
        SpringApplication.run(SystemManagementApplication.class, args);
    }

}
