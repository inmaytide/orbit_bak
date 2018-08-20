package com.inmaytide.orbit.commons.service;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.commons.exception.VersionMismatchedException;
import com.inmaytide.orbit.commons.id.IdGenerator;
import com.inmaytide.orbit.commons.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends AbstractEntity> implements BasicService<T> {

    @Autowired
    private IdGenerator<Long> generator;

    @Override
    public T save(T t) {
        Assert.nonNull(t.getCreator(), "Creator cannot be null");
        t.setId(generator.generate());
        getMapper().save(t);
        return getMapper().get(t.getId());
    }

    @Override
    public void remove(Long id) {
        getMapper().remove(id);
    }

    @Override
    public T update(T t) {
        Assert.nonNull(t.getId(), "ID for updating instance cannot be null");
        Assert.nonNull(t.getUpdater(), "Updater cannot be null");
        Assert.isTrue(getMapper().update(t) != 0, VersionMismatchedException::new);
        return getMapper().get(t.getId());
    }

    @Override
    public T updateSelective(T t) {
        Assert.nonNull(t.getId(), "ID for updating instance cannot be null");
        Assert.nonNull(t.getUpdater(), "Updater cannot be null");
        Assert.isTrue(getMapper().updateSelective(t) != 0, VersionMismatchedException::new);
        return getMapper().get(t.getId());
    }

    @Override
    public Optional<T> get(Long id) {
        return Optional.ofNullable(getMapper().get(id));
    }

    @Override
    public List<T> all() {
        return getMapper().all();
    }

    protected IdGenerator<Long> generator() {
        return this.generator;
    }
}
