package com.inmaytide.orbit.sys;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mybatis.domains.AuditDateAware;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.data.mybatis.support.SqlSessionFactoryBean;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
@EnableMybatisRepositories(mapperLocations = "classpath*:mappers/**/*.xml")
public class SystemManagementApplication {


    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(9999L);
    }

    @Bean
    public AuditDateAware<LocalDateTime> auditDateAware() {
        return LocalDateTime::now;
    }

    public static void main(String... args) {
        SpringApplication.run(SystemManagementApplication.class, args);
    }



    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(HikariDataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }

}
