package com.inmaytide.orbit.commons.id;


import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeIdGenerator implements IdentifierGenerator {

    private final SnowflakeIdWorker idWorker;

    private SnowflakeIdGenerator() {
        idWorker = new SnowflakeIdWorker(1, 1);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return idWorker.nextId();
    }
}
