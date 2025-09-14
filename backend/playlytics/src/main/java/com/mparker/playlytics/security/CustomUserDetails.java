package com.mparker.playlytics.security;

// Imports
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    Collection<? extends GrantedAuthority> authorities;
    String username;
    String password;
    Long authenticatedUserId;


    public CustomUserDetails() {
    }

    public CustomUserDetails(String username, String password, Long authenticatedUserId) {
        this.username = username;
        this.password = password;
        this.authenticatedUserId = authenticatedUserId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public Long getAuthenticatedUserId() {
            return authenticatedUserId;
    }

}
