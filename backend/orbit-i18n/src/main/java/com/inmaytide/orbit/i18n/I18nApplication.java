package com.inmaytide.orbit.i18n;

import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author Moss
 * @since September 22, 2017
 */
@SpringBootApplication
@EnableWebFlux
@EnableDiscoveryClient
@RestController
public class I18nApplication {

    public static void main(String... args) {
        SpringApplication.run(I18nApplication.class, args);
    }

    @Autowired
    private I18nResourceHolder resourceHolder;

    @GetMapping("/lang/{lang}")
    public Mono<Map<String, String>> lang(@PathVariable String lang) {
        return Mono.create(sink -> sink.success(resourceHolder.getResources(lang)));
    }

    @Bean
    @Order(-1)
    public GlobalExceptionHandler errorHandler() {
        return new GlobalExceptionHandler();
    }

}
