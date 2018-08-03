package com.inmaytide.orbit.system;

import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;

@SpringBootApplication
@MapperScan("com.inmaytide.orbit.system.mapper")
public class SystemServiceLauncher {

    @Bean
    @Order(-1)
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }

    public static void main(String[] args) {
        SpringApplication.run(SystemServiceLauncher.class, args);
    }


}
