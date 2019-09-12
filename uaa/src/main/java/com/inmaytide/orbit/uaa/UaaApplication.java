package com.inmaytide.orbit.uaa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.inmaytide.exception.http.handler.servlet.DefaultExceptionHandler;
import com.inmaytide.orbit.uaa.domain.User;
import com.inmaytide.orbit.uaa.utils.AuthenticatedHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class UaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> AuthenticatedHelper.getAuthenticatedUser().map(User::getId);
    }

    @Bean
    @Order(-1)
    public DefaultExceptionHandler exceptionHandler() {
        return new DefaultExceptionHandler();
    }

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(module);
        return mapper;
    }

}
