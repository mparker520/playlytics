package com.mparker.playlytics.service;

// Imports
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service

public class AuthenticationService {

    //<editor-fold desc = "Constructor">

        private final AuthenticationManager authenticationManager;

        public AuthenticationService(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }


    //</editor-fold>

    //<editor-fold desc = "Login Method">

        public Authentication login(String username, String password) {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(token);

        }

    //</editor-fold>


}
