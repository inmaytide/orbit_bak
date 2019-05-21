package com.inmaytide.orbit.uaa.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextHolder.context = applicationContext;
    }

    public static <T> T getBean(Class<T> cls) {
        if (context == null) {
            throw new NullPointerException();
        }
        return context.getBean(cls);
    }
}
