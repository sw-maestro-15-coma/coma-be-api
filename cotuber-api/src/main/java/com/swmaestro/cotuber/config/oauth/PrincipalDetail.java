package com.swmaestro.cotuber.config.oauth;

import com.swmaestro.cotuber.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public record PrincipalDetail(
        User user,
        Map<String, Object> attributes
) implements OAuth2User, UserDetails {
    @Override
    public String getName() {
        return attributes.get("sub").toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().getKey()));
    }

    @Override
    public String getPassword() {
        return user.getOAuthId();
    }

    @Override
    public String getUsername() {
        return String.valueOf(user.getId());
    }
}