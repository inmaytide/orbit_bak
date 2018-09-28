package com.inmaytide.orbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
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

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("orbit-authorization", ps -> ps.path("/oauth/**").uri("lb://orbit-authorization"))
                .route("orbit-captcha", ps -> ps.path("/captcha/**").uri("lb://orbit-captcha"))
                .route("orbit-system", ps -> ps.path("/sys/**").filters(f -> f.rewritePath("/sys/(?<segment>.*)", "/${segment}")).uri("lb://orbit-system"))
                .route("orbit-data-dictionary", ps -> ps.path("/dictionaries/**").uri("lb://orbit-data-dictionary"))
                .route("orbit-attachment", ps -> ps.path("/attachments/**").uri("lb://orbit-attachment"))
                .build();
    }

    public static void main(String... args) {
        SpringApplication.run(ApiGatewayServerLauncher.class, args);
    }

}
