package com.inmaytide.orbit.filter;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

public class JwtResolve {

    public static void main(String... args) {
        Jwt jwt = JwtHelper.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJuYW1lIjoiQWRtaW5pc3RyYXRvciIsImF2YXRhciI6Im51bGwiLCJleHAiOjE1MTcyNjE2ODQsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtaW5pc3RyYXRvciIsInBlcm0tbGlzdCIsInN5cy1tYW5hZ2VtZW50IiwiZGFzaGJvYXJkIl0sImp0aSI6IjdmNmYzNGYxLTQ4NzgtNDVkNy1iNmVkLTVjNzc1MjQyNzNiYyIsImNsaWVudF9pZCI6ImFwcHMifQ.kRUxGrU1NfypYzAUgd2e0jTf5iEBFfe0ELscL543Cx0");
        System.out.println(jwt.getClaims());
    }

}
