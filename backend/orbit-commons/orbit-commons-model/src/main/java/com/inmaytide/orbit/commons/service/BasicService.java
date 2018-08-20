package com.inmaytide.orbit.commons.service;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.commons.mapper.BasicMapper;

import java.util.List;
import java.util.Optional;

public interface BasicService<T extends AbstractEntity> {

    T save(T t);

    void remove(Long id);

    T update(T t);

    T updateSelective(T t);

    Optional<T> get(Long id);

    List<T> all();

    BasicMapper<T> getMapper();

}
