package com.inmaytide.orbit.uaa.domain.id;

import com.inmaytide.orbit.service.external.CoreService;
import com.inmaytide.orbit.uaa.utils.ContextHolder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeIdGenerator implements IdentifierGenerator {

    private CoreService service;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return getService().newId();
    }

    private CoreService getService() {
        if (service == null) {
            this.service = ContextHolder.getBean(CoreService.class);
        }
        return service;
    }

}
