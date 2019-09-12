package com.inmaytide.orbit.core.id.generator;

public class SnowflakeIdGenerator implements IdGenerator<Long> {

    private final SnowflakeIdWorker worker;

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        this.worker = new SnowflakeIdWorker(workerId, dataCenterId);
    }


    @Override
    public Long generate() {
        return worker.nextId();
    }

}
