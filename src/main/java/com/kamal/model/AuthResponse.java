package com.kamal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
