package com.inmaytide.orbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerLauncher {

    public static void main(String... args) {
        SpringApplication.run(DiscoveryServerLauncher.class, args);
    }

}
