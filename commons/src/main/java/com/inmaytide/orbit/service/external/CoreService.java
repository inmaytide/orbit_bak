package com.inmaytide.orbit.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luomiao
 * @since 2019/11/19
 */
@FeignClient("core")
public interface CoreService {

    List<Long> CACHE = new ArrayList<>();

    @GetMapping("/api/ids")
    List<Long> batchGetId();

    default Long newId() {
        if (CACHE.isEmpty()) {
            CACHE.addAll(batchGetId());
        }
        return CACHE.remove(0);
    }

}
