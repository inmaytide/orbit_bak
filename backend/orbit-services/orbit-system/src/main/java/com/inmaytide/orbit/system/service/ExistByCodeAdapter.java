package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.commons.util.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface ExistByCodeAdapter {

    boolean exist(String code, Long ignore);

    default boolean exist(String code, Long ignore, Function<Map, Integer> count) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        ignore = Objects.requireNonNullElse(ignore, -1L);
        return count.apply(Map.of("code", code, "ignore", ignore)) > 0;
    }

    default void assertCodeNotExist(AbstractEntity inst) {
        Assert.nonNull(inst, "Instance to assert cannot be null");
        Method method = ReflectionUtils.findMethod(inst.getClass(), "getCode");
        Assert.nonNull(method, String.format("The getter for \"code\" not found in [%s]", inst.getClass().getName()));
        Object code = ReflectionUtils.invokeMethod(method, inst);
        if (code != null) {
            Long ignore = Objects.requireNonNullElse(inst.getId(), -1L);
            Assert.isTrue(!exist(code.toString(), ignore), "Code is existed");
        }
    }

}
