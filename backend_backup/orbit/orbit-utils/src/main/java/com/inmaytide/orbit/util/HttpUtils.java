package com.inmaytide.orbit.util;

import org.springframework.http.HttpHeaders;

public class HttpUtils {

    public static void disableHttpCache(HttpHeaders headers) {
        headers.setExpires(0L);
        headers.setCacheControl("no-store, no-cache, must-revalidate");
        headers.add("Cache-Control", "post-check=0, pre-check=0");
        headers.setPragma("no-cache");
    }

}
