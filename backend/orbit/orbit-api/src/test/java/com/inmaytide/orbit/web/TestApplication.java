package com.inmaytide.orbit.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestApplication {

    public static void main(String... args) {
        System.out.println(Pattern.matches("^[1|2]$", "1L"));
        System.out.println(Pattern.matches("^[1|2]$", "2"));
        System.out.println(Pattern.matches("^[1|2]$", "3"));
        System.out.println(Pattern.matches("^[1|2]$", "0"));
        System.out.println(Pattern.matches("^[1|2]$", "123123"));
        System.out.println(Pattern.matches("^[1|2]$", "1.2"));

        List<Map<Object, Object>> list1 = new ArrayList<>();
        List<Map<Object, Object>> list2 = new ArrayList<>();

        list1.stream()
                .filter(map -> list2.stream().anyMatch(map1 -> map.get("id").equals(map1.get("id"))))
                .forEach(map -> {
                    // to do sth
                });

        list1.forEach(map -> {
            list2.forEach(map1 -> {
                if (map.get("id").equals(map1.get("id"))) {
                    // do sth
                    System.out.println(11);
                }
            });
        });


        List<Map<Object, Object>> list3 = list1.stream()
                .map(map -> list2.stream()
                                .filter(m -> Objects.equals(m.get("id"), map.get("id")))
                                .findFirst().map(m -> {
                                    map.putAll(m);
                                    return map;
                                }).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }
}
