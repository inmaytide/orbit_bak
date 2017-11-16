package com.inmaytide.orbit.auz.provider;

import java.util.Set;

/**
 * @author Moss
 * @since October 04, 2017
 */
public interface RoleProvider {

    Set<String> listCodesByUsername(String username);

}

