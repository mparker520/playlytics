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
    String email;
    String firstName;
    String displayName;


    public CustomUserDetails() {
    }

    public CustomUserDetails(String username, String password, Long authenticatedUserId, String email, String firstName, String displayName) {
        this.username = username;
        this.password = password;
        this.authenticatedUserId = authenticatedUserId;
        this.email = email;
        this.firstName = firstName;
        this.displayName = displayName;
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

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
