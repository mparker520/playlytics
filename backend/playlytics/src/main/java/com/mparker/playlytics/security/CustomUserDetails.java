package com.mparker.playlytics.security;

// Imports
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {


    //<editor-fold desc="Variables">

        String username;
        String password;
        Long authenticatedUserId;
        String email;
        String firstName;
        String lastName;
        String displayName;


    //</editor-fold>

    //<editor-fold desc="Constructor">

        public CustomUserDetails(String username, String password, Long authenticatedUserId, String email, String firstName, String lastName, String displayName) {
            this.username = username;
            this.password = password;
            this.authenticatedUserId = authenticatedUserId;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.displayName = displayName;
        }


    //</editor-fold>

    //<editor-fold desc="Override Methods">

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
        }

        // Get UserName
        @Override
        public String getPassword() {
            return password;
        }

        // Get Password
        @Override
        public String getUsername() {
            return username;
        }


    //</editor-fold>

    //<editor-fold desc="Getters">

    public Long getAuthenticatedUserId() {
            return authenticatedUserId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }


    //</editor-fold>

}
