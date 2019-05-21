package com.inmaytide.orbit.uaa.utils;

import com.inmaytide.orbit.commons.exception.LoginRestrictedException;
import com.inmaytide.orbit.uaa.auth.config.RestrictLevel;
import com.inmaytide.orbit.uaa.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RestrictUtil {

    private static final String LOGIN_ERROR_COUNT_PREFIX = "login_error_count::";

    private static final String P_NAME_USERNAME = "username";

    private static final long RESTRICT_TIME = 30;

    private static final TimeUnit RESTRICT_TIME_UNIT = TimeUnit.MINUTES;

    private static ValueOperations<String, String> valueOperations;

    public static void increment(HttpServletRequest request) {
        String ip = getIpFromRequest(request);
        increment(ip);

        String username = request.getParameter(P_NAME_USERNAME);
        Integer value = increment(username);
        if (value > RestrictLevel.FORBIDDEN_15.getUpper()) {
            ContextHolder.getBean(UserService.class).disableUser(username);
        }
    }


    public static boolean neededCaptcha(HttpServletRequest request) {
        Integer value = getValidValue(request).getValue();
        return value > RestrictLevel.NORMAL.getUpper();
    }

    public static void forbidden(HttpServletRequest request) {
        Map.Entry<String, Integer> entry = getValidValue(request);
        Integer value = entry.getValue();
        int forbidTime;
        if (value > RestrictLevel.CAPTCHE.getUpper() && value <= RestrictLevel.FORBIDDEN_5.getUpper()) {
            forbidTime = 5;
        } else if (value > RestrictLevel.FORBIDDEN_5.getUpper() && value <= RestrictLevel.FORBIDDEN_10.getUpper()) {
            forbidTime = 10;
        } else if (value > RestrictLevel.FORBIDDEN_10.getUpper() && value <= RestrictLevel.FORBIDDEN_15.getUpper()) {
            forbidTime = 15;
        } else if (value > RestrictLevel.FORBIDDEN_15.getUpper()) {
            throw new LoginRestrictedException("-1");
        } else {
            return;
        }
        Long expire = getValueOperations().getOperations().getExpire(getCacheKey(entry.getKey()));
        if (expire != null && RESTRICT_TIME * 60 - expire < forbidTime * 60) {
            throw new LoginRestrictedException(String.valueOf(forbidTime));
        }
    }


    private static Integer increment(String key) {
        if (StringUtils.isBlank(key)) {
            return -1;
        }
        Integer value = getValue(key) + 1;
        getValueOperations().set(getCacheKey(key), String.valueOf(value), RESTRICT_TIME, RESTRICT_TIME_UNIT);
        return value;
    }

    private static Integer getValue(String key) {
        String value = getValueOperations().get(getCacheKey(key));
        return StringUtils.isBlank(value) ? 0 : NumberUtils.createInteger(value);
    }

    private static Map.Entry<String, Integer> getValidValue(HttpServletRequest request) {
        String ip = getIpFromRequest(request);
        Integer ipValue = getValue(ip);

        String username = request.getParameter(P_NAME_USERNAME);
        Integer usernameValue = getValue(username);

        return ipValue >= usernameValue ? Map.entry(ip, ipValue) : Map.entry(username, usernameValue);
    }

    private static String getCacheKey(String key) {
        return LOGIN_ERROR_COUNT_PREFIX + key;
    }


    private static ValueOperations<String, String> getValueOperations() {
        if (valueOperations == null) {
            StringRedisTemplate template = ContextHolder.getBean(StringRedisTemplate.class);
            valueOperations = template.opsForValue();
        }
        return valueOperations;
    }

    private static String getIpFromRequest(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
}