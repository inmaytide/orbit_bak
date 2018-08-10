package com.inmaytide.orbit.commons.id;


import java.io.Serializable;

public class SnowflakeIdGenerator implements IdGenerator<Long> {

    private final SnowflakeIdWorker idWorker;

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        idWorker = new SnowflakeIdWorker(workerId, dataCenterId);
    }

    public Long generate() {
        return idWorker.nextId();
    }
}
