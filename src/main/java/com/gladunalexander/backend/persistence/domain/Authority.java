package com.gladunalexander.backend.persistence.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Alexander Gladun
 * Simple implementation for {@link GrantedAuthority}
 */
public class Authority implements GrantedAuthority {

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
