package com.inmaytide.orbit;

import com.inmaytide.orbit.filter.SessionHeaderPassFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableZuulProxy
@SpringCloudApplication
public class ApiGatewayApplication {

    @Autowired
    private CorsProperties corsProperties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SessionHeaderPassFilter sessionHeaderPassFilter() {
        return new SessionHeaderPassFilter();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperties.getMapping(), corsProperties.translate());
        return new CorsFilter(source);
    }

    public static void main(String... args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
