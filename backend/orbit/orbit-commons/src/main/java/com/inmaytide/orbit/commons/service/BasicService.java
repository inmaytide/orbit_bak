package com.inmaytide.orbit.commons.service;

import com.inmaytide.orbit.commons.query.Conditions;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author Moss
 * @since September 15, 2017
 */
public interface BasicService<R extends MybatisRepository<T, ID>, T, ID  extends Serializable> {

    String DEFAULT_SEPARATOR_CHARS = ",";

    default void pagingInformation(Conditions conditions, Pageable pageable) {
        conditions.put("size", pageable.getPageSize());
        conditions.put("offset", pageable.getOffset());
    }

    default ID[] split(final String text, Function<String, ID> mapper, IntFunction<ID[]> generator) {
        return split(text, mapper).toArray(generator);
    }


    default <A, C> C split(final String text, Function<String, ID> mapper, Collector<ID, A, C> collector) {
        return split(text, mapper).collect(collector);
    }

    default Stream<ID> split(final String text, Function<String, ID> mapper) {
        Assert.hasText(text, "The id string must have text; it must not be null, empty, or blank");
        String[] splitted = StringUtils.split(text, DEFAULT_SEPARATOR_CHARS);
        return Arrays.stream(splitted).map(StringUtils::trim).map(mapper);
    }


    default Optional<T> get(ID id) {
        return getRepository().findById(id);
    }

    default T insert(T inst) {
        Assert.notNull(inst, "The instance to insert must not be null");
        return getRepository().insert(inst);
    }

    default T update(T inst) {
        Assert.notNull(inst, "The instance to update must not be null");
        return getRepository().update(inst);
    }

    default boolean exist(ID id) {
        return getRepository().existsById(id);
    }

    default boolean exist(List<ID> ids) {
        Assert.notEmpty(ids, "The ids parameter must not be empty");
        return IterableUtils.size(getRepository().findAllById(ids)) == ids.size();
    }

    default void remove(List<ID> ids) {
        Assert.notEmpty(ids, "The ids parameter must not be empty");
        ids.forEach(getRepository()::deleteById);
    }

    R getRepository();

}
