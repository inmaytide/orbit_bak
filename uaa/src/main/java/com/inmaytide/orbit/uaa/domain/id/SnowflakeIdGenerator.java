package com.inmaytide.orbit.uaa.domain.id;

import com.inmaytide.orbit.uaa.client.CoreClient;
import com.inmaytide.orbit.uaa.utils.ContextHolder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeIdGenerator implements IdentifierGenerator {

    private CoreClient client;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return getClient().newId();
    }

    private CoreClient getClient() {
        if (client == null) {
            this.client = ContextHolder.getBean(CoreClient.class);
        }
        return client;
    }

}
