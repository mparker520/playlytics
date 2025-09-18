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


        Authentication authentication = authenticationService.login(authRequestDTO.username(), authRequestDTO.password());

        request.getSession(true);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, request, response);

      CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthResponseDTO(true, principal.getAuthenticatedUserId(), principal.getEmail(), principal.getFirstName(), principal.getDisplayName()));

    }

    //<editor-fold>

}
