package com.inmaytide.orbit.uaa.domain.id;

import com.inmaytide.orbit.id.IdGenerator;
import com.inmaytide.orbit.uaa.utils.ContextHolder;
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
