package com.inmaytide.orbit.auz.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisShiroCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisShiroCacheManager.class);

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    private final RedisCacheManager cacheManager;

    public RedisShiroCacheManager(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("Get RedisCache instance by name {}", name);
        //noinspection unchecked
        Cache<K, V> cache = caches.get(name);
        if (cache == null) {
            cache = new RedisShiroCache<>(cacheManager.getCache(name));
            caches.put(name, cache);
        }
        return cache;
    }

}
