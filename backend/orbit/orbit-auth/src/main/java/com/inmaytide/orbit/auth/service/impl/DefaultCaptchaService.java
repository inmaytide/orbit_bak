package com.inmaytide.orbit.auth.service.impl;

import com.inmaytide.orbit.auth.exception.IncorrectCaptchaException;
import com.inmaytide.orbit.auth.service.CaptchaService;
import com.inmaytide.orbit.commons.consts.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class DefaultCaptchaService implements CaptchaService {

    private static final String SERVICE_URL_VALIDATION_CAPTCHA = "http://orbit-captcha/{captcha}";

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public void validation(String captcha) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.HEADER_NAME_SESSION_ID, getSessionId());
        HttpEntity<Boolean> request = new HttpEntity<>(headers);
        Boolean isValid = restTemplate.exchange(SERVICE_URL_VALIDATION_CAPTCHA,
                HttpMethod.GET,
                request,
                Boolean.class,
                verifyCaptcha(captcha)).getBody();
        if (isValid == null || !isValid) {
            throw new IncorrectCaptchaException("Wrong captcha");
        }
    }

    private String verifyCaptcha(String captcha) {
        return StringUtils.isBlank(captcha) ? "-1" : captcha;
    }


    private String getSessionId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader(Constants.HEADER_NAME_SESSION_ID);
        }
        return null;
    }
}
