package com.inmaytide.orbit.captcha;

import com.inmaytide.orbit.captcha.handler.CaptchaHandler;
import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import org.patchca.color.RandomColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
public class CaptchaApplication {

    public static void main(String... args) {
        SpringApplication.run(CaptchaApplication.class, args);
    }

    @Bean
    public ConfigurableCaptchaService configurableCaptchaService() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setColorFactory(new RandomColorFactory());
        cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
        cs.setHeight(50);
        return cs;
    }

    @Bean
    public RouterFunction<?> routers(CaptchaHandler handler) {
        RouterFunction<?> routers = route(GET("/"), handler::getCaptcha)
                .and(route(GET("/{captcha}"), handler::validation))
                .andOther(route(RequestPredicates.all(), GlobalExceptionHandler::notFound));
        return nest(path("/captcha"), routers);
    }

    @Bean
    public HttpHandler httpHandler(RouterFunction<?> routers) {
        return WebHttpHandlerBuilder
                .webHandler(RouterFunctions.toWebHandler(routers))
                .exceptionHandler(new GlobalExceptionHandler())
                .build();
    }


}
