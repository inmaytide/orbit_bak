package com.inmaytide.orbit.auth.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.inmaytide.orbit.commons.Constants.HEADER_NAME_CAPTCHA_NAME;

@FeignClient("orbit-captcha")
public interface CaptchaClient {

    @GetMapping("/captcha/{captcha}")
    ObjectNode validate(@RequestHeader(HEADER_NAME_CAPTCHA_NAME) String cacheName,
                        @PathVariable("captcha") String captcha);
}
