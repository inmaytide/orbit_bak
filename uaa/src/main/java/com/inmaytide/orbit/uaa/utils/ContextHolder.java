package com.inmaytide.orbit.uaa.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        ContextHolder.context = applicationContext;
    }

    public static <T> T getBean(Class<T> cls) {
        Assert.notNull(context, "");
        Assert.notNull(cls, "");
        return context.getBean(cls);
    }
}
