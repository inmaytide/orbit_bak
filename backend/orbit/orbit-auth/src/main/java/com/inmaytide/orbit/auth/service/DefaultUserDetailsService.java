package com.inmaytide.orbit.auth.service;

import com.inmaytide.orbit.domain.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DefaultUserDetailsService implements UserDetailsService {

    private static final String SERVICE_URL_BASE = "http://orbit-system-management/";
    private static final String SERVICE_URL_GET_USER = "/users/{username}";
    private static final String SERVICE_URL_GET_AUTHORITIES = "/permissions/codes/{username}";
    private static final String SERVICE_URL_GET_ROLES = "/roles/codes/{username}";

    @Autowired
    private LoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction;

    private WebClient client() {
        return WebClient.builder()
                .baseUrl(SERVICE_URL_BASE)
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }


    private Optional<User> getByUsername(String username) {
        return Optional.ofNullable(client().get()
                .uri(SERVICE_URL_GET_USER, username)
                .retrieve()
                .bodyToMono(User.class)
                .block());
    }

    private Stream<String> getAuthorities(String uri, Object... uriVariables) {
        return client().get().uri(uri, uriVariables)
                .retrieve()
                .bodyToFlux(String.class)
                .toStream();
    }

    private Set<GrantedAuthority> getAuthorities(String username) {
        return Stream.concat(getAuthorities(SERVICE_URL_GET_AUTHORITIES, username), getAuthorities(SERVICE_URL_GET_ROLES, username).map(authority -> "ROLE_" + authority))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasText(username, "username cannot be empty");
        User user = getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .accountLocked(user.isLocked())
                .authorities(getAuthorities(username))
                .build();
    }

}
