package com.inmaytide.orbit.commons.id;


public class SnowflakeIdGenerator {

    private final SnowflakeIdWorker idWorker;

    private SnowflakeIdGenerator() {
        idWorker = new SnowflakeIdWorker(1, 1);
    }

    private static SnowflakeIdGenerator getInstance() {
        return IdGeneratorHolder.INSTANCE;
    }

    public static long generate() {
        return getInstance().idWorker.nextId();
    }

    private static final class IdGeneratorHolder {
        private static final SnowflakeIdGenerator INSTANCE = new SnowflakeIdGenerator();
    }
}
