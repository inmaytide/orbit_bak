package com.inmaytide.orbit.auz.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.util.Assert;

/**
 * @author Moss
 * @since September 23, 2017
 */
public class Md5Utils {

    private static final String USER_PASSWORD_MD5_SALT_PATTREN = "%sf391d46923fe39c1";

    public static String salt(String origin) {
        Assert.hasText(origin, "The parameter of salt method cannot be empty.");
        return String.format(USER_PASSWORD_MD5_SALT_PATTREN, origin);
    }

    public static String md5(String origin, String salt) {
        Assert.hasText(origin, "The string to be encrypted can not be empty.");
        return new Md5Hash(origin, salt(salt)).toString();
    }

}
