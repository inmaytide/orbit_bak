package com.inmaytide.orbit.handler.sys;

import com.inmaytide.orbit.domain.sys.DataDictionary;
import com.inmaytide.orbit.handler.AbstractHandler;
import com.inmaytide.orbit.service.sys.DataDictionaryService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since November 07, 2017
 */
@Component
public class DataDictionaryHandler extends AbstractHandler {

    @Resource
    private DataDictionaryService service;

    public Mono<ServerResponse> listByCategory(ServerRequest request) {
        return request.queryParam("category").map(service::listByCategory)
                .map(Flux::fromIterable)
                .map(flux -> ok().body(flux, DataDictionary.class))
                .orElseThrow(IllegalArgumentException::new);
    }

}
