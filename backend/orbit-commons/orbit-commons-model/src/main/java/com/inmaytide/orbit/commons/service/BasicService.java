package com.inmaytide.orbit.commons.service;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.commons.exception.VersionMismatchedException;
import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.commons.id.IdGenerator;
import com.inmaytide.orbit.commons.util.Assert;

import java.util.List;
import java.util.Optional;

public interface BasicService<T extends AbstractEntity> {

    default T save(T t) {
        Assert.nonNull(t.getCreator(), "Creator cannot be null");
        t.setId(getIdGenerator().generate());
        getMapper().save(t);
        return getMapper().get(t.getId());
    }

    default void remove(Long id) {
        getMapper().remove(id);
    }

    default T update(T t) {
        Assert.nonNull(t.getId(), "ID for updating instance cannot be null");
        Assert.nonNull(t.getUpdater(), "Updater cannot be null");
        Assert.isTrue(getMapper().update(t) != 0, VersionMismatchedException::new);
        return getMapper().get(t.getId());
    }

    default T updateSelective(T t) {
        Assert.nonNull(t.getId(), "ID for updating instance cannot be null");
        Assert.nonNull(t.getUpdater(), "Updater cannot be null");
        Assert.isTrue(getMapper().updateSelective(t) != 0, VersionMismatchedException::new);
        return getMapper().get(t.getId());
    }

    default Optional<T> get(Long id) {
        return Optional.ofNullable(getMapper().get(id));
    }

    default List<T> all() {
        return getMapper().all();
    }

    BasicMapper<T> getMapper();

    IdGenerator<Long> getIdGenerator();
}
