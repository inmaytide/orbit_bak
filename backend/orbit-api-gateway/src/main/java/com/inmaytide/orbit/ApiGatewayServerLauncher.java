package com.inmaytide.orbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@SpringCloudApplication
public class ApiGatewayServerLauncher {

    private final CorsProperties corsProperties;

    @Autowired
    public ApiGatewayServerLauncher(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Bean
    public CorsWebFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration(corsProperties.getMapping(), corsProperties.translate());
        return new CorsWebFilter(source);
    }

//    @Bean
//    public DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient) {
//        return new DiscoveryClientRouteDefinitionLocator(discoveryClient);
//    }

    public static void main(String... args) {
        SpringApplication.run(ApiGatewayServerLauncher.class, args);
    }

}
