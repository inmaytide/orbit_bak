package com.inmaytide.orbit.captcha.service.impl;

import com.inmaytide.orbit.captcha.service.CaptchaService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

@Service
public class DefaultCaptchaService implements CaptchaService {

    private static final Logger log = LoggerFactory.getLogger(DefaultCaptchaService.class);

    private static final String CACHE_CAPTCHA_KEY_PATTERN = "redis-captcha:%s";

    private static final String DEFAULT_IMAGE_FORMAT = "png";

    private ConfigurableCaptchaService configurableCaptchaService;

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public DefaultCaptchaService(ConfigurableCaptchaService configurableCaptchaService, StringRedisTemplate stringRedisTemplate) {
        this.configurableCaptchaService = configurableCaptchaService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Resource generateCaptcha(String cacheName) {
        return generateCaptcha(DEFAULT_IMAGE_FORMAT, cacheName);
    }

    @Override
    public Resource generateCaptcha(String format, String cacheName) {
        FileSystemResource resource = new FileSystemResource(FilenameUtils.concat(System.getProperty("java.io.tmpdir"), cacheName + ".png"));
        try (OutputStream os = resource.getOutputStream()) {
            cacheName = generateCacheName(cacheName);
            String captcha = EncoderHelper.getChallangeAndWriteImage(configurableCaptchaService, "png", os);
            stringRedisTemplate.opsForValue().set(cacheName, captcha, 15, TimeUnit.MINUTES);
            log.debug("Captcha generated: {key: '{}', value: '{}'}", cacheName, captcha);
        } catch (IOException e) {
            log.error("Failed to generate captcha. => {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return resource;
    }

    @Override
    public boolean validation(String captcha, String cacheName) {
        String key = generateCacheName(cacheName);
        boolean isValid = hasCaptcha(key) && StringUtils.equalsIgnoreCase(captcha, getServerCaptcha(key));
        log.debug("Validation captcha: {key: '{}', input: '{}', isValid: '{}'}", key, captcha, isValid);
        return isValid;
    }

    private boolean hasCaptcha(String key) {
        boolean hasCaptcha = stringRedisTemplate.hasKey(key);
        log.debug("Check redis have captcha: {key: '{}', hasCaptcha: '{}'}", key, hasCaptcha);
        return hasCaptcha;
    }

    private String getServerCaptcha(String key) {
        String serverCaptcha = stringRedisTemplate.opsForValue().get(key);
        stringRedisTemplate.delete(key);
        log.debug("Get captcha from redis: {key: '{}', serverCaptcha: '{}'}", key, serverCaptcha);
        return serverCaptcha;
    }

    private String generateCacheName(String cacheName) {
        return String.format(CACHE_CAPTCHA_KEY_PATTERN, cacheName);
    }
}
