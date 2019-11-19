package com.inmaytide.orbit.core;

import com.inmaytide.orbit.core.id.generator.IdGenerator;
import com.inmaytide.orbit.core.id.generator.SnowflakeIdGenerator;
import com.inmaytide.orbit.core.id.rest.IdResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@EnableWebFlux
@SpringBootApplication
@EnableDiscoveryClient
public class CoreApplication {

    @Value("${application.id.worker-id:1}")
    private Integer workerId;

    @Value("${application.id.data-center-id:1}")
    private Integer dataCenterId;

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    @Bean
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(workerId, dataCenterId);
    }

}
