package com.inmaytide.orbit.auz.realm;

import com.inmaytide.orbit.auz.provider.PermissionProvider;
import com.inmaytide.orbit.auz.provider.RoleProvider;
import com.inmaytide.orbit.auz.provider.UserProvider;
import com.inmaytide.orbit.consts.Constants;
import com.inmaytide.orbit.domain.sys.User;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

public abstract class AbstractRealm extends AuthorizingRealm {

    @Lazy
    @Resource
    private UserProvider userProvider;

    @Lazy
    @Resource
    private RoleProvider roleProvider;

    @Lazy
    @Resource
    private PermissionProvider permissionProvider;

    protected AbstractRealm() {
        setAuthenticationCachingEnabled(true);
        setAuthorizationCachingEnabled(true);
        setAuthenticationCacheName(Constants.AUTHENTICATION_CACHE_NAME + getClass().getSimpleName().toLowerCase());
        setAuthorizationCacheName(Constants.AUTHORIZATION_CACHE_NAME + getClass().getSimpleName().toLowerCase());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Assert.notNull(principals, "PrincipalCollection method argument cannot be null.");
        User user = (User) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(getRoleProvider().listCodesByUsername(user.getUsername()));
        info.setStringPermissions(getPermissionProvider().listCodesByUsername(user.getUsername()));
        return info;
    }

    protected Optional<User> getUserByUsername(String username) {
        return getUserProvider().getByUsername(username)
                .map(this::checkUserStatus);
    }

    protected User checkUserStatus(User user) {
        if (user.isLocked()) {
            throw new LockedAccountException(user.getUsername());
        }
        return user;
    }

    protected UserProvider getUserProvider() {
        return userProvider;
    }

    protected RoleProvider getRoleProvider() {
        return roleProvider;
    }

    protected PermissionProvider getPermissionProvider() {
        return permissionProvider;
    }
}
