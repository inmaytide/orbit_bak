package com.inmaytide.orbit.auth;

import com.inmaytide.orbit.auth.provider.CaptchaProvider;
import com.inmaytide.orbit.auth.provider.DefaultCaptchaProvider;
import com.inmaytide.orbit.auth.provider.FormAuthenticationProvider;
import org.patchca.color.RandomColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

    public static void main(String... args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ConfigurableCaptchaService configurableCaptchaService() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setColorFactory(new RandomColorFactory());
        cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
        cs.setHeight(50);
        return cs;
    }


    @Bean
    public CaptchaProvider captchaProvider(ConfigurableCaptchaService configurableCaptchaService, StringRedisTemplate stringRedisTemplate) {
        return new DefaultCaptchaProvider(configurableCaptchaService, stringRedisTemplate);
    }

    @Bean
    public FormAuthenticationProvider customizeAuthenticationProvider() {
        return new FormAuthenticationProvider();
    }


}
