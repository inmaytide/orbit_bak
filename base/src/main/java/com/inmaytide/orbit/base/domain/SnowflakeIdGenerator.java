package com.inmaytide.orbit.base.domain;

import com.inmaytide.orbit.base.ContextHolder;
import com.inmaytide.orbit.id.IdGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeIdGenerator implements IdentifierGenerator {

    private IdGenerator generator;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return getGenerator().generate();
    }


    public IdGenerator getGenerator() {
        if (generator == null) {
            this.generator = ContextHolder.getBean(IdGenerator.class);
        }
        return generator;
    }
}
