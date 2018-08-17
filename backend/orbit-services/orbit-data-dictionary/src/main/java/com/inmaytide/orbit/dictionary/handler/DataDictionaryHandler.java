package com.inmaytide.orbit.dictionary.handler;

import com.inmaytide.orbit.commons.util.JsonUtils;
import com.inmaytide.orbit.dictionary.dao.DataDictionaryMapper;
import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * @author Moss
 * @since November 28, 2017
 */
@Component
public class DataDictionaryHandler {

    @Autowired
    private DataDictionaryMapper mapper;

    private List<DataDictionary> list(String category) {
        return mapper.listByCategory(category);
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest request) {
        return request.queryParam("category")
                .map(this::list)
                .map(Flux::fromIterable)
                .map(flux -> ok().body(flux, DataDictionary.class))
                .orElse(noContent().build());
    }

    @NonNull
    public Mono<ServerResponse> add(ServerRequest request) {
        return request
                .bodyToMono(DataDictionary.class)
                .map(this::validate)
                .map(inst -> {
                    mapper.save(inst);
                    return mapper.get(inst.getId());
                })
                .map(Mono::just)
                .flatMap(mono -> created(request.uri()).body(mono, DataDictionary.class));
    }


    private Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    private <T> Map<String, String> transformErrorMessage(Set<ConstraintViolation<T>> violations) {
        Map<String, String> message = new HashMap<>();
        violations.forEach(violation -> message.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return message;
    }

    private <T> T validate(T instance) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(instance);
        Assert.isTrue(CollectionUtils.isEmpty(violations), JsonUtils.serialize(transformErrorMessage(violations)));
        return instance;
    }

}
