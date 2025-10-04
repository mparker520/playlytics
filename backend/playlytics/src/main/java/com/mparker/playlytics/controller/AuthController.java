package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.AuthResponseDTO;
import com.mparker.playlytics.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


    //<editor-fold desc="Me Endpoint to Verify Authentication on Refresh">

        @GetMapping("/me")
        public ResponseEntity<?> getCurrentUser(Authentication authentication) {

            // Return Unauthorized if Not Authenticated
            if (authentication == null ||  !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Return User DTO if Authenticated
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

               AuthResponseDTO userDTO = new AuthResponseDTO(
                       true,
                       user.getAuthenticatedUserId(),
                       user.getEmail(),
                       user.getFirstName(),
                       user.getLastName(),
                       user.getDisplayName()
                );

                return ResponseEntity.ok(userDTO);

        }


    //</editor-fold>


}
