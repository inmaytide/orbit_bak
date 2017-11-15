package com.inmaytide.orbit;

import com.inmaytide.orbit.filter.SessionHeaderPassFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringCloudApplication
public class ApiGatewayApplication {

    @Bean
    public SessionHeaderPassFilter sessionHeaderPassFilter() {
        return new SessionHeaderPassFilter();
    }

    public static void main(String... args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
