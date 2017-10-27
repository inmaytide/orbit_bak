package com.inmaytide.orbit.i18n.configuration;

import com.inmaytide.orbit.i18n.I18nMessages;
import com.inmaytide.orbit.i18n.I18nResourceHolder;
import com.inmaytide.orbit.i18n.I18nResourceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * @author Moss
 * @since September 22, 2017
 */
@Configuration
public class I18nConfiguration {

    @Resource
    private MessageSource messageSource;

    @Value("#{ @environment['orbit.i18n.resource-cache'] ?: true }")
    private boolean isCache;

    @Value("#{ @environment['spring.messages.basename'] ?: 'messages' }")
    private String basename;

    @Bean
    @ConditionalOnMissingBean
    public I18nMessages i18nMessages() {
        return new I18nMessages(messageSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public I18nResourceHolder i18nResourceHolder() {
        return new I18nResourceHolder(basename, isCache);
    }

    @Bean
    @ConditionalOnMissingBean
    public I18nResourceProvider i18nResourceProvider() {
        return new I18nResourceProvider();
    }

}
