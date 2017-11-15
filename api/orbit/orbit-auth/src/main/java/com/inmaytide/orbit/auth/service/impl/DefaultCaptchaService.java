package com.inmaytide.orbit.auth.service.impl;

import com.inmaytide.orbit.auth.exception.IncorrectCaptchaException;
import com.inmaytide.orbit.auth.service.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class DefaultCaptchaService implements CaptchaService {

    private static final String SERVICE_URL_VALIDATION_CAPTCHA = "http://orbit-captcha/{captcha}";

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public void validation(String captcha) {
        if (StringUtils.isBlank(captcha)) {
            captcha = "-1";
        }
        RequestContextHolder.getRequestAttributes();
        Boolean isValid = restTemplate.getForObject(SERVICE_URL_VALIDATION_CAPTCHA, Boolean.class, captcha);
        if (isValid == null || !isValid) {
            throw new IncorrectCaptchaException("Wrong captcha");
        }
    }
}
