package com.inmaytide.orbit.uaa;

import com.inmaytide.orbit.id.IdGenerator;
import com.inmaytide.orbit.id.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class UaaApplication {

    @Value("${application.id.worker-id:1}")
    private Integer workerId;
    @Value("${application.id.data-center-id:1}")
    private Integer dataCenterId;

    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

    @Bean
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(workerId, dataCenterId);
    }

}
