package com.inmaytide.orbit.dictionary.handler;

import com.inmaytide.orbit.util.JsonUtils;
import com.inmaytide.orbit.dictionary.dao.DataDictionaryRepository;
import com.inmaytide.orbit.dictionary.domain.DataDictionary;
import com.inmaytide.orbit.exception.PathNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import java.util.*;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since November 28, 2017
 */
@Component
public class DataDictionaryHandler {

    private static final Sort DEFAULT_SORT = new Sort(Sort.Direction.ASC, "sort");

    @Autowired
    private DataDictionaryRepository repository;

//    private static final Map<String, List<DataDictionary>> ENUM_DICTIONARIES = new ConcurrentHashMap<>();
//    public static final String KEY_PERMISSION_CATEGORY = "permission_category";
//    public static final String KEY_HTTP_METHOD = "http_method";
//
//    static {
//        ENUM_DICTIONARIES.put(KEY_PERMISSION_CATEGORY, Stream.of(PermissionCategory.values())
//                .map(value -> DataDictionary.withPermissionCategory(value).build()).collect(Collectors.toList()));
//
//        ENUM_DICTIONARIES.put(KEY_HTTP_METHOD, Stream.of(HttpMethod.values())
//                .map(value -> DataDictionary.withHttpMethod(value).build()).collect(Collectors.toList()));
//    }

    private List<DataDictionary> list(String category) {
//        if (ENUM_DICTIONARIES.containsKey(category)) {
//            return ENUM_DICTIONARIES.get(category);
//        }
        return repository.findByCategory(category, DEFAULT_SORT);
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest request) {
        return request.queryParam("category")
                .map(this::list)
                .map(Flux::fromIterable)
                .map(flux -> ok().body(flux, DataDictionary.class))
                .orElseThrow(() -> new PathNotFoundException(request.path()));
    }

    @NonNull
    public Mono<ServerResponse> add(ServerRequest request) {
        return request
                .bodyToMono(DataDictionary.class)
                .map(this::validate)
                .map(repository::save)
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
        Assert.isTrue(CollectionUtils.isEmpty(violations), JsonUtils.getJsonString(transformErrorMessage(violations)));
        return instance;
    }

}
