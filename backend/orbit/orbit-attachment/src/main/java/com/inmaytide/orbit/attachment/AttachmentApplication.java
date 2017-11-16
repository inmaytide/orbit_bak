package com.inmaytide.orbit.attachment;

import com.inmaytide.orbit.attachment.handler.AttachmentHandler;
import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.data.mybatis.support.SqlSessionFactoryBean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@EnableWebFlux
@SpringBootApplication
@EnableDiscoveryClient
@EnableMybatisRepositories
@EnableTransactionManagement
public class AttachmentApplication {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(HikariDataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(AttachmentApplication.class, args);
    }

    @Bean
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public RouterFunction<?> routers(AttachmentHandler handler, GlobalExceptionHandler exceptionHandler) {
        RouterFunction<?> routers = route(POST("/").and(accept(MULTIPART_FORM_DATA)), handler::upload)
                .and(route(GET("/{id}"), handler::download))
                .and(route(DELETE("/{ids}"), handler::remove))
                .and(route(DELETE("/"), handler::removeByBelong))
                .andOther(route(RequestPredicates.all(), exceptionHandler::notFound));
        return routers;
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction routers, GlobalExceptionHandler exceptionHandler) {
        return WebHttpHandlerBuilder
                .webHandler(RouterFunctions.toWebHandler(routers, HandlerStrategies.builder().build()))
                .exceptionHandler(exceptionHandler)
                .build();
    }

}
