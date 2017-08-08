package com.gladunalexander.backend.persistence.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Gladun
 * Implementation for {@link UserDetails}
 */
public class CustomUserDetails implements UserDetails {

    private User user;

    private Set<GrantedAuthority> grantedAuthorities;

    public CustomUserDetails(User user) {
        this.user = user;
        grantedAuthorities = new HashSet<>();
        this.user.getRoles()
                .forEach(role -> this.grantedAuthorities.add(new Authority(role.getName())));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
