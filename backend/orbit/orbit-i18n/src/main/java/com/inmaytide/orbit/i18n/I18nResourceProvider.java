package com.inmaytide.orbit.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Frontend i18n resource provider
 *
 * @author Moss
 * @since September 11, 2017
 */
public class I18nResourceProvider {

    @Autowired
    private I18nResourceHolder resourceHolder;

    @Nonnull
    public Mono<ServerResponse> lang(ServerRequest request) {
        String lang = request.pathVariable("lang");
        return ServerResponse.ok().body(Mono.justOrEmpty(resourceHolder.getResources(lang)), Map.class);
    }

    public RouterFunction<?> routers() {
        return route(GET("/lang/{lang}"), this::lang);
    }

}
