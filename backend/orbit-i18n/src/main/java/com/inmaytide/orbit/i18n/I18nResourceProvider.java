package com.inmaytide.orbit.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Frontend i18n resource provider
 *
 * @author Moss
 * @since September 11, 2017
 */
@Component
public class I18nResourceProvider {

    @Autowired
    private I18nResourceHolder resourceHolder;

    @NonNull
    public Mono<ServerResponse> lang(ServerRequest request) {
        String lang = request.pathVariable("lang");
        return ServerResponse.ok().body(Mono.justOrEmpty(resourceHolder.getResources(lang)), Map.class);
    }

}
