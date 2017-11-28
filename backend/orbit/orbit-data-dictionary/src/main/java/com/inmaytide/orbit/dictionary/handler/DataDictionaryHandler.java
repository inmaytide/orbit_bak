package com.inmaytide.orbit.dictionary.handler;

import com.inmaytide.orbit.dictionary.dao.DataDictionaryRepository;
import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import com.inmaytide.orbit.exception.PathNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since November 28, 2017
 */
@Component
public class DataDictionaryHandler {

    @Autowired
    private DataDictionaryRepository repository;

    public Mono<ServerResponse> list(ServerRequest request) {
        return request.queryParam("category")
                .map(repository::findByCategory)
                .map(flux -> ok().body(flux, DataDictionary.class))
                .orElseThrow(() -> new PathNotFoundException(request.path()));
    }

}
