package com.inmaytide.orbit.auz.util;

import com.inmaytide.orbit.consts.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class TokenUtils {

    private static final String DEFAULT_ISSUER = "issuer";

    private static final long DEFAULT_VAILD_PERIOD = 24 * 60 * 60 * 1000;

    public static String generate(String id, String subject) {
        return generate(id, subject, DEFAULT_ISSUER, DEFAULT_VAILD_PERIOD);
    }

    private static byte[] getApiKeySecretBytes() {
        try {
            return Base64.getEncoder().encode(Constants.APP_KEY.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generate(String id, String subject, String issuer, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Key key = new SecretKeySpec(getApiKeySecretBytes(), signatureAlgorithm.getJcaName());

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, key);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims getClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(getApiKeySecretBytes())
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static boolean validate(String token) {
        try {
            Claims claims = getClaims(token);
            Date date = claims.getExpiration();
            return date.compareTo(new Date()) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

}
