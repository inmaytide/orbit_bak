package com.inmaytide.orbit.i18n;

import org.springframework.context.i18n.LocaleContextHolder;
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
public class I18nResourceHolder {

    private Map<Locale, Map<String, String>> cache = new ConcurrentHashMap<>(4);

    private String basename;

    private boolean isCache;

    public I18nResourceHolder(String basename, boolean isCache) {
        this.basename = basename;
        this.isCache = isCache;
    }

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
