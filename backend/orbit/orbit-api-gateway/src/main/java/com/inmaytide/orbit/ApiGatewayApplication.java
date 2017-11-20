package com.inmaytide.orbit;

//import com.inmaytide.orbit.filter.SessionHeaderPassFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import static org.springframework.cloud.gateway.handler.predicate.RoutePredicates.path;

@SpringCloudApplication
public class ApiGatewayApplication {

    @Autowired
    private CorsProperties corsProperties;

    //
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    @Bean
//    public SessionHeaderPassFilter sessionHeaderPassFilter() {
//        return new SessionHeaderPassFilter();
//    }
//
    @Bean
    public CorsWebFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration(corsProperties.getMapping(), corsProperties.translate());
        return new CorsWebFilter(source);
    }

    @Bean
    public DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient);
    }

    @Bean
    public RouteLocator locator() {
        return Routes.locator()
                .route("orbit-i18n")
                .predicate(path("/lang/**"))
                .uri("lb://orbit-i18n")
                .route("orbit-auth")
                .predicate(path("/auth/**"))
                .uri("lb://orbit-auth")
                .build();
    }

    public static void main(String... args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
