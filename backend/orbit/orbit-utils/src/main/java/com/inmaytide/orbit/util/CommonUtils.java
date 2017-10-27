package com.inmaytide.orbit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.UUID;

public class CommonUtils {

    public static final Charset UTF_8 = Charset.forName("utf-8");

    public static final Charset ISO_8859_1 = Charset.forName("iso-8859-1");

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String encode(String text, Charset source, Charset target) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        return new String(text.getBytes(source), target);
    }

    public static byte[] getJsonBytes(Object o) {
        try {
            return new ObjectMapper().writerFor(o.getClass()).writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getJsonString(Object instance) {
        try {
            return new ObjectMapper().writerFor(instance.getClass()).writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
