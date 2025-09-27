package com.mparker.playlytics.controller;

// Imports

import com.mparker.playlytics.dto.RegisteredPlayerDTO;
import com.mparker.playlytics.dto.RegisteredPlayerResponseDTO;
import com.mparker.playlytics.dto.RegisteredPlayerUpdateDTO;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.RegisteredPlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegisteredPlayerController {

    //<editor-fold desc = "Constructor">

    private final RegisteredPlayerService registeredPlayerService;

    public RegisteredPlayerController(RegisteredPlayerService registeredPlayerService) {
        this.registeredPlayerService = registeredPlayerService;
    }

    //</editor-fold>

    //<editor-fold desc="GET Mapping">
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<RegisteredPlayerResponseDTO> getProfile(
            @AuthenticationPrincipal CustomUserDetails principal) {

        RegisteredPlayerResponseDTO playerResponseDTO = registeredPlayerService.getProfile(principal.getAuthenticatedUserId());
        return ResponseEntity.ok(playerResponseDTO);
    }
    //</editor-fold>


    //<editor-fold desc = "POST Mapping">


    @PostMapping("/create-account")
    public ResponseEntity<RegisteredPlayerResponseDTO> createRegisteredPlayer(
            @RequestBody RegisteredPlayerDTO registeredPlayerDTO) {

        RegisteredPlayerResponseDTO registeredPlayerResponseDTO = registeredPlayerService.createRegisteredPlayer(registeredPlayerDTO);
        return ResponseEntity.ok(registeredPlayerResponseDTO);

    }

    //</editor-fold>


    //<editor-fold desc = "PATCH Mapping">

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/profile")
    public ResponseEntity<RegisteredPlayerUpdateDTO> updateRegisteredPlayer(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody RegisteredPlayerUpdateDTO registeredPlayerUpdateDTO) {

        RegisteredPlayerUpdateDTO registeredPlayerResponseDTO = registeredPlayerService.updateRegisteredPlayer(principal.getAuthenticatedUserId(), registeredPlayerUpdateDTO);
        return ResponseEntity.ok(registeredPlayerResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mapping">

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/account")
    public ResponseEntity<Void> deleteRegisteredPlayer(
            @AuthenticationPrincipal CustomUserDetails principal) {

            registeredPlayerService.deleteRegisteredPlayer(principal.getAuthenticatedUserId());
            return ResponseEntity.noContent().build();

    }

    //</editor-fold>


}
