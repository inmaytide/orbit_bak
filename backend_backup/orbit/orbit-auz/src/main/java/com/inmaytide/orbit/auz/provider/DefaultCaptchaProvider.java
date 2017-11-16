package com.inmaytide.orbit.auz.provider;

import com.inmaytide.orbit.consts.Constants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Supplier;

public class DefaultCaptchaProvider implements CaptchaProvider {

    private static final Logger log = LoggerFactory.getLogger(DefaultCaptchaProvider.class);

    private ConfigurableCaptchaService configurableCaptchaService;

    private StringRedisTemplate stringRedisTemplate;

    public DefaultCaptchaProvider(ConfigurableCaptchaService configurableCaptchaService, StringRedisTemplate stringRedisTemplate) {
        this.configurableCaptchaService = configurableCaptchaService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Resource generateCaptcha(String format, String keySuffix) {
        FileSystemResource resource = new FileSystemResource(FilenameUtils.concat(System.getProperty("java.io.tmpdir"), keySuffix + ".png"));
        try (OutputStream os = resource.getOutputStream()) {
            String captcha = EncoderHelper.getChallangeAndWriteImage(configurableCaptchaService, "png", os);
            stringRedisTemplate.opsForValue().set(generateCacheCaptchaKey(keySuffix), captcha);
        } catch (IOException e) {
            log.error("Failed to generate captcha. => {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return resource;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void validation(String captcha, String keySuffix, Supplier<? extends RuntimeException> exceptionSupplier) {
        String key = generateCacheCaptchaKey(keySuffix);
        if (stringRedisTemplate.hasKey(key)
                && !StringUtils.equalsIgnoreCase(captcha, getServerCaptcha(key))) {
            throw exceptionSupplier.get();
        }
    }

    private String getServerCaptcha(String key) {
        String serverCaptcha = stringRedisTemplate.opsForValue().get(key);
        stringRedisTemplate.delete(key);
        return serverCaptcha;
    }

    private String generateCacheCaptchaKey(String keySuffix) {
        return String.format(Constants.CACHE_CAPTCHA_KEY_PATTERN, keySuffix);
    }

}
