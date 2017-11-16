package com.inmaytide.orbit.auz.handler;

import com.inmaytide.orbit.auz.util.SessionUtils;
import org.apache.shiro.authz.UnauthenticatedException;

public enum DefaultFilterHandlers {

    anon(values -> {}),
    authc(values -> {
        if (!SessionUtils.getSubject().isAuthenticated()) {
            throw new UnauthenticatedException();
        }
    })
    ;


    private FilterHandler filterHandler;

    DefaultFilterHandlers(FilterHandler filterHandler) {
        this.filterHandler = filterHandler;
    }

    public FilterHandler getFilterHandler() {
        return filterHandler;
    }
}
