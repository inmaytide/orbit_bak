package com.inmaytide.orbit.dictionary.handler;

import com.inmaytide.orbit.commons.util.JsonUtils;
import com.inmaytide.orbit.dictionary.dao.DataDictionaryRepository;
import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import com.inmaytide.orbit.exception.PathNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
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
                .map(Flux::fromIterable)
                .map(flux -> ok().body(flux, DataDictionary.class))
                .orElseThrow(() -> new PathNotFoundException(request.path()));
    }

    public Mono<ServerResponse> add(ServerRequest request) {
        return request
                .bodyToMono(DataDictionary.class)
                .map(this::validate)
                .map(repository::save)
                .map(Mono::just)
                .flatMap(mono -> created(request.uri()).body(mono, DataDictionary.class));
    }


    public Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public <T> Map<String, String> transformErrorMessage(Set<ConstraintViolation<T>> violations) {
        Map<String, String> message = new HashMap<>();
        violations.forEach(violation -> message.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return message;
    }

    public <T> T validate(T instance) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(instance);
        Assert.isTrue(CollectionUtils.isEmpty(violations), JsonUtils.getJsonString(transformErrorMessage(violations)));
        return instance;
    }

}
