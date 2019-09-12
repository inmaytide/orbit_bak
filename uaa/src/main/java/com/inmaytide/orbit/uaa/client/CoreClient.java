package com.inmaytide.orbit.uaa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luomiao
 * @since 2019/09/10
 */
@FeignClient("core")
public interface CoreClient {

    List<Long> cache = new ArrayList<>();

    @GetMapping("/api/id")
    Long get();

    @GetMapping("/api/id/batch")
    List<Long> batch();

    default Long newId() {
        if (cache.isEmpty()) {
            cache.addAll(batch());
        }
        return cache.remove(0);
    }

}
