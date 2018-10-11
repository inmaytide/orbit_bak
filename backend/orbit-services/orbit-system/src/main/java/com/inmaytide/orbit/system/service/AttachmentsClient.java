package com.inmaytide.orbit.system.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("orbit-attachment")
public interface AttachmentsClient {

    @PatchMapping("/attachments/{id}/as-formal")
    void asFormal(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

}
