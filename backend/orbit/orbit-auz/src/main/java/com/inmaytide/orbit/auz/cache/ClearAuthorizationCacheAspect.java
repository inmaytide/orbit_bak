package com.inmaytide.orbit.auz.cache;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ClearAuthorizationCacheAspect {

    private static final Logger log = LoggerFactory.getLogger(ClearAuthorizationCacheAspect.class);

    private DefaultSecurityManager securityManager;

    @Autowired
    public ClearAuthorizationCacheAspect(DefaultSecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @AfterReturning(value = "@annotation(com.inmaytide.orbit.auz.cache.annotation.ClearAuthorizationCache)")
    public void afterReturnAdvice() {
        if (CollectionUtils.isNotEmpty(securityManager.getRealms())) {
            securityManager.getRealms()
                    .stream()
                    .filter(realm -> realm instanceof AuthorizingRealm)
                    .map(realm -> (AuthorizingRealm) realm)
                    .forEach(this::clearAuthorizationCache);
            log.debug("Successfully cleared authorization cache.");
        }
    }

    private void clearAuthorizationCache(AuthorizingRealm realm) {
        realm.getAuthorizationCache().clear();
        realm.getAuthenticationCache().clear();
    }
}
