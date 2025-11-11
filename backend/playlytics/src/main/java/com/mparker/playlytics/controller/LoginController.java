package com.mparker.playlytics.controller;

// Imports=
import com.mparker.playlytics.dto.AuthRequestDTO;
import com.mparker.playlytics.dto.AuthResponseDTO;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    //<editor-fold desc = "Constructor">

    private final AuthenticationService authenticationService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final CsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //</editor-fold>

    //<editor-fold desc = "Login Endpoint">

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody AuthRequestDTO authRequestDTO,
            HttpServletRequest request,
            HttpServletResponse response) {

        // Call Authentication Service
        Authentication authentication = authenticationService.login(authRequestDTO.username(), authRequestDTO.password());

        // Ensure there is a session
        request.getSession(true);

        // Create an Empty Security Context
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // Set Security Context with Authentication
        context.setAuthentication(authentication);

        // Save Security Context in the Security Context Repository
        securityContextRepository.saveContext(context, request, response);

        // CSRF Token
        CsrfToken token = csrfTokenRepository.generateToken(request);
        csrfTokenRepository.saveToken(token, request, response);

        // Create Custom User Detail from Authentication Principal
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        // Return AuthResponseDTO
        return ResponseEntity.ok(new AuthResponseDTO(true, principal.getAuthenticatedUserId(), principal.getEmail(), principal.getFirstName(), principal.getLastName(), principal.getDisplayName()));

    }


    //</editor-fold>

}
