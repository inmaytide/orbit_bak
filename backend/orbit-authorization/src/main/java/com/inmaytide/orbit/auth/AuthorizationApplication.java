package com.inmaytide.orbit.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


@SpringBootApplication
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableFeignClients
public class AuthorizationApplication {

    public static void main(String... args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
