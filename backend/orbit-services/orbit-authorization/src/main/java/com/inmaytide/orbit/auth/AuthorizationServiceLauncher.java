package com.inmaytide.orbit.auth;

import com.inmaytide.orbit.commons.Constants;
import feign.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


@EnableOAuth2Client
@EnableFeignClients
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
public class AuthorizationServiceLauncher {

    @Bean
    public RequestInterceptor authRequestInterceptor() {
        return (template) -> template.header(Constants.HEADER_NAME_INSIDE_TOKEN, Constants.INSIDE_TOKEN);
    }

    public static void main(String... args) {
        SpringApplication.run(AuthorizationServiceLauncher.class, args);
    }

}
