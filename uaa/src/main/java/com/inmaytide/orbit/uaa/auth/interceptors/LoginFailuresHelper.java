package com.inmaytide.orbit.uaa.auth.interceptors;

import com.inmaytide.orbit.uaa.utils.ContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author luomiao
 * @since 2019/09/10
 */
public class LoginFailuresHelper {

    private static final String CN_LOGIN_FAILURES_COUNT = "login_failures_count::%s";

    private static final int LIMIT_NEEDED_CAPTCHA = 2;

    private static final int LIMIT_REFUSED = 5;

    private static ValueOperations<String, String> valueOperations;

    static boolean neededCaptcha(HttpServletRequest request) {
        return getFailuresCount(request) > LIMIT_NEEDED_CAPTCHA;
    }

    static boolean refused(HttpServletRequest request) {
        return getFailuresCount(request) > LIMIT_REFUSED;
    }

    public static void increment(HttpServletRequest request) {
        String cacheName = getCacheName(request);
        Integer count = getFailuresCount(request);
        getValueOperations().set(cacheName, String.valueOf(++count), 15, TimeUnit.MINUTES);
    }

    private static ValueOperations<String, String> getValueOperations() {
        if (valueOperations == null) {
            StringRedisTemplate template = ContextHolder.getBean(StringRedisTemplate.class);
            valueOperations = template.opsForValue();
        }
        return valueOperations;
    }

    private static Integer getFailuresCount(HttpServletRequest request) {
        String value = getValueOperations().get(getCacheName(request));
        return StringUtils.isNotBlank(value) ? NumberUtils.createInteger(value) : 0;
    }

    private static String getCacheName(HttpServletRequest request) {
        String ip = getIpFromRequest(request);
        return String.format(CN_LOGIN_FAILURES_COUNT, ip);
    }

    private static boolean ipValid(String ip) {
        return StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip);
    }

    private static String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        String from = request.getHeader("X-Forwarded-For");
        if (ipValid(from)) {
            int index = from.indexOf(",");
            return index != -1 ? from.substring(0, index) : from;
        }
        if (ipValid(ip)) {
            return ip;
        }
        from = request.getHeader("Proxy-Client-IP");
        if (!ipValid(from)) {
            from = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!ipValid(from)) {
            from = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!ipValid(from)) {
            from = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!ipValid(from)) {
            from = request.getRemoteAddr();
        }
        return from;
    }

}
