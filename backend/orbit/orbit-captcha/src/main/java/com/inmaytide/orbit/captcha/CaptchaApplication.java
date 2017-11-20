package com.inmaytide.orbit.captcha;

import com.inmaytide.orbit.captcha.handler.CaptchaHandler;
import org.patchca.color.RandomColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
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
        return route(GET("/captcha"), handler::getCaptcha)
                .and(route(GET("/captcha/{captcha}"), handler::validation));
    }


}
