package com.inmaytide.orbit.pk;


public class IdGenerator {

    private final SnowflakeIdWorker idWorker;

    private static IdGenerator getInstance() {
        return IdGeneratorHolder.INSTANCE;
    }

    private static final class IdGeneratorHolder {
        private static final IdGenerator INSTANCE = new IdGenerator();
    }

    private IdGenerator() {
        idWorker = new SnowflakeIdWorker(1, 1);
    }

    public static long nextId() {
        return getInstance().idWorker.nextId();
    }



}
