package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.GhostPlayerDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.dto.GhostPlayerUpdateDTO;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.GhostPlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController

public class GhostPlayerController {

    //<editor-fold desc = "Constructor">

    private final GhostPlayerService ghostPlayerService;

    public GhostPlayerController(GhostPlayerService ghostPlayerService) {
        this.ghostPlayerService = ghostPlayerService;
    }

    //</editor-fold>

    //<editor-fold desc = "POST Mapping">

    @PostMapping("/ghost-players")
    public ResponseEntity<GhostPlayerResponseDTO> createGhostPlayer(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody GhostPlayerDTO ghostPlayerDTO) {

        GhostPlayerResponseDTO ghostPlayerResponseDTO = ghostPlayerService.createNewGhostPlayer(ghostPlayerDTO, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(ghostPlayerResponseDTO);

    }
    //</editor-fold>

    //<editor-fold desc = "PATCH Mapping">

    @PatchMapping("/ghost-players/{ghostPlayerId}")
    public ResponseEntity<GhostPlayerResponseDTO> updateGhostPlayer(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable Long ghostPlayerId,
            @RequestBody GhostPlayerUpdateDTO ghostPlayerUpdateDTO) {

        GhostPlayerResponseDTO ghostPlayerResponseDTO = ghostPlayerService.updateGhostPlayer(ghostPlayerId, ghostPlayerUpdateDTO, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(ghostPlayerResponseDTO);

    }

    //</editor-fold>

}
