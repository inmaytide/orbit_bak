package com.inmaytide.orbit;

import com.inmaytide.orbit.commons.id.SnowflakeIdWorker;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class SnowflakeIdWorkerTest extends TestCase {

    @Test
    public void test() {
        Set<Long> cache = new HashSet<>();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 100000; i++) {
            long id = idWorker.nextId();
            assertFalse(cache.contains(id));
            cache.add(id);
            System.out.println(id);
        }
        assertTrue(cache.size() == 100000);
    }

}
