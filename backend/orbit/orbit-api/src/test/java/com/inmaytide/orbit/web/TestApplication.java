package com.inmaytide.orbit.web;

import java.util.regex.Pattern;

public class TestApplication {

    public static void main(String... args) {
        System.out.println(Pattern.matches("^[1|2]$", "1L"));
        System.out.println(Pattern.matches("^[1|2]$", "2"));
        System.out.println(Pattern.matches("^[1|2]$", "3"));
        System.out.println(Pattern.matches("^[1|2]$", "0"));
        System.out.println(Pattern.matches("^[1|2]$", "123123"));
        System.out.println(Pattern.matches("^[1|2]$", "1.2"));

    }
}
