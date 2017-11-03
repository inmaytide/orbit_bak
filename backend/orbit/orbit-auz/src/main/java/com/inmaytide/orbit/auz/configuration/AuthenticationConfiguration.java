package com.inmaytide.orbit.auz.configuration;

import com.inmaytide.orbit.auz.cache.RedisSessionDao;
import com.inmaytide.orbit.auz.cache.RedisShiroCacheManager;
import com.inmaytide.orbit.auz.filter.AuthenticatingFilter;
import com.inmaytide.orbit.auz.filter.AuthenticatingFilterDefinitions;
import com.inmaytide.orbit.auz.filter.JwtAuthenticationFilter;
import com.inmaytide.orbit.auz.filter.PathsMatcher;
import com.inmaytide.orbit.auz.util.SessionUtils;
import com.inmaytide.orbit.auz.provider.CaptchaProvider;
import com.inmaytide.orbit.auz.provider.DefaultCaptchaProvider;
import com.inmaytide.orbit.auz.realm.AbstractRealm;
import com.inmaytide.orbit.auz.realm.JwtRealm;
import com.inmaytide.orbit.auz.strategy.FirstExceptionStrategy;
import com.inmaytide.orbit.domain.sys.User;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.patchca.color.RandomColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mybatis.domains.AuditDateAware;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Moss
 * @since October 04, 2017
 */
@Configuration
public class AuthenticationConfiguration {

    @Bean
    @ConditionalOnMissingBean(CaptchaProvider.class)
    public ConfigurableCaptchaService configurableCaptchaService() {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setColorFactory(new RandomColorFactory());
        cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
        cs.setHeight(50);
        return cs;
    }

    @Bean
    @ConditionalOnMissingBean(CaptchaProvider.class)
    public CaptchaProvider captchaProvider(ConfigurableCaptchaService configurableCaptchaService, StringRedisTemplate stringRedisTemplate) {
        return new DefaultCaptchaProvider(configurableCaptchaService, stringRedisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(AuditDateAware.class)
    public AuditDateAware<LocalDateTime> auditDateAware() {
        return LocalDateTime::now;
    }

    @Bean
    @ConditionalOnMissingBean(AuditorAware.class)
    public AuditorAware<Long> auditorAware() {
        return () -> SessionUtils.getCurrentUser().map(User::getId);
    }

    @Bean
    public Authorizer authorizer() {
        return new ModularRealmAuthorizer();
    }

    @Bean
    public AuthenticationStrategy authenticationStrategy() {
        return new FirstExceptionStrategy();
    }

    @Bean
    protected Authenticator authenticator(AuthenticationStrategy authenticationStrategy) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(authenticationStrategy);
        return authenticator;
    }

    @Bean
    public RedisSessionDao sessionDAO() {
        return new RedisSessionDao();
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDao) {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionDAO(sessionDao);
        return sessionManager;
    }

    @Bean
    public CorsWebFilter corsWebFilter(CorsProperties corsProperties) {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        corsConfigurationSource.registerCorsConfiguration(corsProperties.getMapping(), corsProperties.translate());
        return new CorsWebFilter(corsConfigurationSource);
    }

    @Bean
    public PathsMatcher pathsMatcher(AuthenticatingFilterDefinitions filterChainDefinitions) {
        return new PathsMatcher(filterChainDefinitions);
    }

    @Bean
    public AuthenticatingFilter authenticatingFilter(PathsMatcher pathsMatcher) {
        return new AuthenticatingFilter(pathsMatcher);
    }

    @Bean
    public JwtAuthenticationFilter jwtOrAuthenticationFilter(PathsMatcher pathsMatcher) {
        return new JwtAuthenticationFilter(pathsMatcher);
    }

    @Bean
    public AuthenticatingFilterDefinitions filterChainDefinitions() {
        AuthenticatingFilterDefinitions bean = new AuthenticatingFilterDefinitions();
        bean.addDefinition("/login", "anon");
        bean.addDefinition("/captcha", "anon");
        bean.addDefinition("/lang/*", "anon");
        bean.addDefinition("/**", "authc");
        return bean;
    }

    @Bean
    public RedisShiroCacheManager redisShiroCacheManager(@SuppressWarnings("SpringJavaAutowiringInspection") RedisCacheManager redisCacheManager) {
        return new RedisShiroCacheManager(redisCacheManager);
    }

    @Bean
    public DefaultSecurityManager securityManager(RedisShiroCacheManager redisShiroCacheManager,
                                                  SessionManager sessionManager,
                                                  Authenticator authenticator,
                                                  JwtRealm jwtRealm, AbstractRealm formRealm) {
        DefaultSecurityManager bean = new DefaultSecurityManager();
        bean.setCacheManager(redisShiroCacheManager);
        bean.setSessionManager(sessionManager);
        bean.setAuthenticator(authenticator);
        bean.setRealms(List.of(jwtRealm, formRealm));
        return bean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }


}
