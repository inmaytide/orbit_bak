package com.inmaytide.orbit.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author luomiao
 * @since 2019/09/27
 */
@FeignClient("uaa")
public interface UaaService {

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> refreshToken(@RequestParam Map<String, String> body);

}
