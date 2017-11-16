package com.inmaytide.orbit.auz.util;

import com.inmaytide.orbit.domain.sys.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Optional;

public class SessionUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static <T> Optional<T> getSessionAttribute(String key) {
        //noinspection unchecked
        return Optional.ofNullable((T) getSession().getAttribute(key));
    }

    public static void setAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Optional<User> getCurrentUser() {
        return Optional.ofNullable((User) getSubject().getPrincipal());
    }


}
