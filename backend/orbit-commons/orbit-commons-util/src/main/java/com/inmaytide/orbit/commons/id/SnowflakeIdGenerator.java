package com.inmaytide.orbit.commons.id;


import java.io.Serializable;

public class SnowflakeIdGenerator {

    private final SnowflakeIdWorker idWorker;

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        idWorker = new SnowflakeIdWorker(workerId, dataCenterId);
    }

    public Serializable generate() {
        return idWorker.nextId();
    }
}
