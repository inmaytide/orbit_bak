package com.inmaytide.orbit.auth;

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

    private static final String HEADER_NAME_INSIDE_TOKEN = "inside-service-token";
    private static final String INSIDE_TOKEN = "MmY3ZDA4YjY1MDA2OGUzZDA2YWVmZTY3MTc0NjFlOTc0ZGEzZTJiNTc3ZDM4NDJhNmUyMDRjMGZjMDQ2YTM1ZQ==";


    @Bean
    public RequestInterceptor authRequestInterceptor() {
        return (template) -> template.header(HEADER_NAME_INSIDE_TOKEN, INSIDE_TOKEN);
    }

    public static void main(String... args) {
        SpringApplication.run(AuthorizationServiceLauncher.class, args);
    }

}
