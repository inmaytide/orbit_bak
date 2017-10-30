package com.inmaytide.orbit.util;

import com.inmaytide.orbit.holder.WebExchangeHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

public class ApiUtils {

    public static String getBaseUrl() {
        URI uri = WebExchangeHolder.get().getRequest().getURI();
        return uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort();
    }

    public static WebClient.RequestHeadersUriSpec<?> delete() {
        HttpHeaders httpHeaders = WebExchangeHolder.get().getRequest().getHeaders();
        return (WebClient.RequestHeadersUriSpec<?>) WebClient.create(getBaseUrl()).delete().headers(headers -> {
            headers.addAll(httpHeaders);
        });
    }

}
