package com.inmaytide.orbit.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SystemManagementApplication {


    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(9999L);
    }

    public static void main(String... args) {
        SpringApplication.run(SystemManagementApplication.class, args);
    }


}
