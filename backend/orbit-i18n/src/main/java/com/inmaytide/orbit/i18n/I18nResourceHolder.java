package com.inmaytide.orbit.i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Moss
 * @since September 22, 2017
 */
@Component
public class I18nResourceHolder {

    @Value("#{ @environment['orbit.i18n.resource-cache'] ?: true }")
    private boolean isCache;

    @Value("#{ @environment['spring.messages.basename'] ?: 'messages' }")
    private String basename;

    private Map<Locale, Map<String, String>> cache = new ConcurrentHashMap<>(4);

    private Locale resolveLocale(String lang) {
        Assert.hasText(lang, "The argument => lang must be not empty.");
        return Locale.forLanguageTag(lang);
    }


    public Map<String, String> getResources() {
        Locale locale = LocaleContextHolder.getLocale();
        return getResources(locale);
    }

    public Map<String, String> getResources(String lang) {
        Locale locale = resolveLocale(lang);
        return getResources(locale);
    }

    private Map<String, String> generateI18nResources(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
        Map<String, String> map = new HashMap<>(bundle.keySet().size());
        bundle.keySet().forEach(key -> map.put(key, bundle.getString(key)));
        if (!map.isEmpty() && isCache) {
            cache.put(locale, map);
        }
        return map;
    }

    private Map<String, String> getResources(Locale locale) {
        return cache.getOrDefault(locale, generateI18nResources(locale));
    }

}
