package com.inmaytide.orbit.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.inmaytide.orbit.commons.exception.handler.GlobalExceptionHandler;
import com.inmaytide.orbit.commons.id.IdGenerator;
import com.inmaytide.orbit.commons.id.SnowflakeIdGenerator;
import com.inmaytide.orbit.commons.util.DateTimeUtils;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@SpringBootApplication
@MapperScan("com.inmaytide.orbit.system.mapper")
public class SystemServiceLauncher {

    @Value("${orbit.server.work-id:0}")
    private Long workId;

    @Value("${orbit.server.data-center-id:0}")
    private Long dataCenterId;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtils.FORMATTER_DEFAULT_DATETIME));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtils.FORMATTER_DEFAULT_DATETIME));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(module);
        return mapper;
    }

    @Bean
    @Order(-1)
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(workId, dataCenterId);
    }

    /**
     * 临时添加自己定义datasource 解决spring boot 2.1.0.m4版本中启动报错的问题
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SystemServiceLauncher.class, args);
    }


}
