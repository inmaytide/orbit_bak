package com.inmaytide.orbit.authorization.service.external;

import com.inmaytide.orbit.dto.UserDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.inmaytide.orbit.Constants.HEADER_ANONYMOUS_TOKEN;

@FeignClient("base")
public interface UserService {

    @GetMapping("/api/users/u/{username}")
    @Headers(HEADER_ANONYMOUS_TOKEN)
    UserDto getUserByUsername(@PathVariable String username);

}
