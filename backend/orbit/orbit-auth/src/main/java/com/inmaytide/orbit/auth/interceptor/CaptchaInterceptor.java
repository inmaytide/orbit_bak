package com.inmaytide.orbit.auth.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.auth.exception.BadCaptchaException;
import com.inmaytide.orbit.commons.consts.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Moss
 * @since November 18, 2017
 */
public class CaptchaInterceptor implements WebRequestInterceptor {

    private LoadBalancerExchangeFilterFunction exchangeFilterFunction;

    public CaptchaInterceptor(LoadBalancerExchangeFilterFunction exchangeFilterFunction) {
        this.exchangeFilterFunction = exchangeFilterFunction;
    }

    private String checkCaptcha(String captcha) {
        return StringUtils.isBlank(captcha) ? "-1" : captcha;
    }


    @Override
    public void preHandle(WebRequest request) throws Exception {
        ServletWebRequest webRequest = (ServletWebRequest) request;
        String value = webRequest.getRequest().getCookies()[0].getValue();
        ObjectNode response = WebClient.builder().baseUrl("http://orbit-captcha")
                .filter(exchangeFilterFunction)
                .build()
                .get()
                .uri("/captcha/{captcha}", checkCaptcha(request.getParameter("captcha")))
                .header(Constants.HEADER_NAME_SESSION_ID, value)
                .retrieve()
                .bodyToMono(ObjectNode.class)
                .block();

        if (response == null) {
            throw new BadCaptchaException();
        }

        JsonNode isValid = response.get("isValid");
        if (isValid.isNull() || !isValid.asBoolean()) {
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
