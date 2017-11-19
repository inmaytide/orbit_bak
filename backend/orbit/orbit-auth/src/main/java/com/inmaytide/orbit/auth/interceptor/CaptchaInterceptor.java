package com.inmaytide.orbit.auth.interceptor;

import com.inmaytide.orbit.auth.exception.BadCaptchaException;
import com.inmaytide.orbit.commons.consts.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Moss
 * @since November 18, 2017
 */
public class CaptchaInterceptor implements WebRequestInterceptor {

    private static final String SERVICE_URL_CHECK_CAPTCHA = "http://orbit-captcha/{captcha}";

    private RestTemplate template;

    public CaptchaInterceptor(RestTemplate template) {
        this.template = template;
    }

    private String checkCaptcha(String captcha) {
        return StringUtils.isBlank(captcha) ? "-1" : captcha;
    }


    @Override
    public void preHandle(WebRequest request) throws Exception {
        String captcha = checkCaptcha(request.getParameter("captcha"));
        String value = request.getHeader(Constants.HEADER_NAME_SESSION_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.HEADER_NAME_SESSION_ID, value);
        HttpEntity<Boolean> httpEntity = new HttpEntity<>(headers);
        Boolean isValid = template.exchange(SERVICE_URL_CHECK_CAPTCHA, HttpMethod.GET, httpEntity, Boolean.class, captcha).getBody();
        if (isValid == null || !isValid) {
            throw new BadCaptchaException();
        }
    }

    @Override
    public void postHandle(WebRequest request, @Nullable ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, @Nullable Exception ex) throws Exception {

    }
}
