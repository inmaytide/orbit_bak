package com.inmaytide.orbit.attachment;

import com.inmaytide.orbit.attachment.handler.AttachmentHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableMybatisRepositories
@EnableWebFlux
@EnableTransactionManagement
@PropertySource(ignoreResourceNotFound = true, value = "classpath:orbit-attachment.yaml")
public class OrbitAttachmentApplication {

    @Bean
    public RouterFunction<?> routers(AttachmentHandler handler) {
        RouterFunction<?> routers = route(POST("/").and(accept(MULTIPART_FORM_DATA)), handler::upload)
                .and(route(GET("/{id}"), handler::download))
                .and(route(DELETE("/{ids}"), handler::remove))
                .and(route(DELETE("/"), handler::removeByBelong));
        return nest(path("/attachments"), routers);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrbitAttachmentApplication.class, args);
    }

}
