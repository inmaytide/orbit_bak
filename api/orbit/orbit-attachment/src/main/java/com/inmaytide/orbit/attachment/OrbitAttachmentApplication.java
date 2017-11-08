package com.inmaytide.orbit.attachment;

import com.inmaytide.orbit.attachment.handler.AttachmentHandler;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.data.mybatis.support.SqlSessionFactoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@EnableWebFlux
@SpringBootApplication
@EnableDiscoveryClient
@EnableMybatisRepositories
@EnableTransactionManagement
public class OrbitAttachmentApplication {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(HikariDataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }

    @Bean
    public RouterFunction<?> routers(AttachmentHandler handler) {
        RouterFunction<?> routers = route(POST("/").and(accept(MULTIPART_FORM_DATA)), handler::upload)
                .and(route(GET("/{id}"), handler::download))
                .and(route(DELETE("/{ids}"), handler::remove))
                .and(route(DELETE("/"), handler::removeByBelong));
        return nest(path("/attachments"), routers)
                .andOther(route(RequestPredicates.all(), (request) -> status(HttpStatus.NOT_FOUND).body(Mono.just("123") , String.class)));
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction routers) {
        return WebHttpHandlerBuilder
                .webHandler(RouterFunctions.toWebHandler(routers, HandlerStrategies.empty().build()))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrbitAttachmentApplication.class, args);
    }

}
