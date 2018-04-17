package com.inmaytide.orbit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JsonUtils {

    public static byte[] serializeAsBytes(Object o) {
        try {
            return new ObjectMapper().writerFor(o.getClass()).writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String serialize(Object o) {
        try {
            return new ObjectMapper().writerFor(o.getClass()).writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectNode deserialize(String str) {
        return deserialize(ObjectNode.class, str);
    }


    public static <T> T deserialize(Class<T> cls, String str) {
        try {
            return new ObjectMapper().readValue(str, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
