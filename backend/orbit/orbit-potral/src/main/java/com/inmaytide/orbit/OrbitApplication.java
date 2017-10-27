package com.inmaytide.orbit;

import com.inmaytide.orbit.attachment.handler.AttachmentHandler;
import com.inmaytide.orbit.auz.filter.AuthenticatingFilter;
import com.inmaytide.orbit.auz.filter.JwtAuthenticationFilter;
import com.inmaytide.orbit.auz.handler.AuzHandler;
import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.filter.ServerWebExchangeFilter;
import com.inmaytide.orbit.router.SysRouters;
import com.inmaytide.orbit.i18n.I18nResourceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Moss
 * @since October 02, 2017
 */
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableWebFlux
public class OrbitApplication {

    @Value("#{ @environment['server.root-path'] ?: '/' }")
    private String rootPath;

    @Bean
    public RouterFunction<?> routers(I18nResourceProvider i18nResourceProvider,
                                     GlobalExceptionHandler exceptionHandler,
                                     SysRouters sysRouters,
                                     AttachmentHandler attachmentHandler,
                                     AuzHandler auzHandler) {
        return nest(RequestPredicates.path(rootPath), i18nResourceProvider.routers()
                .andOther(auzHandler.routers())
                .andOther(sysRouters.routers())
        .andOther(attachmentHandler.routers()))
                .andOther(route(RequestPredicates.all(), exceptionHandler::notFound));
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction routers,
                                   CorsWebFilter corsWebFilter,
                                   AuthenticatingFilter authenticatingFilter,
                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                   GlobalExceptionHandler exceptionHandler) {
        return WebHttpHandlerBuilder
                .webHandler(RouterFunctions.toWebHandler(routers))
                .filter(new ServerWebExchangeFilter(),
                        corsWebFilter,
                        jwtAuthenticationFilter,
                        authenticatingFilter)
                .exceptionHandler(exceptionHandler).build();
    }

    public static void main(String... args) {
        SpringApplication.run(OrbitApplication.class, args);
    }

}
