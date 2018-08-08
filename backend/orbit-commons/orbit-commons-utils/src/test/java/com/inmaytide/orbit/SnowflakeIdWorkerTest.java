package com.inmaytide.orbit;

import com.inmaytide.orbit.commons.id.SnowflakeIdWorker;
import com.inmaytide.orbit.commons.id.UUIDGenerator;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SnowflakeIdWorkerTest extends TestCase {

    @Test
    public void test() {
        Set<Long> cache = new HashSet<>();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 100000; i++) {
            long id = idWorker.nextId();
            assertFalse(cache.contains(id));
            cache.add(id);
            //System.out.println(id);
        }
        assertTrue(cache.size() == 100000);
    }

    @Test
    public void testUUID() {
        long b = Instant.now().toEpochMilli();
        for (int i = 0; i < 1; i++) {
            String s = UUID.randomUUID().toString();
            String v = s.replaceAll("-", "");
        }
        long e = Instant.now().toEpochMilli();
        System.out.println(e - b);

        b = Instant.now().toEpochMilli();
        for (int i = 0; i < 1000000; i++) {
            UUIDGenerator.generate();
        }
        e = Instant.now().toEpochMilli();
        System.out.println(e - b);
    }

}
