package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.OwnedGameResponseDTO;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.GameInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class OwnedGameController {

    //<editor-fold desc = "Constructor">

    private final GameInventoryService gameInventoryService;

    public OwnedGameController(GameInventoryService gameInventoryService) {
        this.gameInventoryService = gameInventoryService;
    }

    //</editor-fold>

    //<editor-fold desc = "GET Mapping">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/owned-games")
    public ResponseEntity<List<OwnedGameResponseDTO>> getOwnedGames(
            @AuthenticationPrincipal CustomUserDetails principal)  {

            List<OwnedGameResponseDTO> allOwnedGames = gameInventoryService.findAllByRegisteredPlayerId(principal.getAuthenticatedUserId());
            return ResponseEntity.ok(allOwnedGames);

    }

    //</editor-fold>

    //<editor-fold desc = "POST Mapping">

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/owned-games/{gameId}")
    public ResponseEntity<OwnedGameResponseDTO> addOwnedGame(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("gameId") Long gameId)  {
        OwnedGameResponseDTO ownedGameResponseDTO = gameInventoryService.saveOwnedGame(gameId, principal.getAuthenticatedUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ownedGameResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mappings">

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/owned-games/{ownedGameId}")
    public ResponseEntity<Void> deleteOwnedGame(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable ("ownedGameId") Long ownedGameId) {
            gameInventoryService.deleteByIdAndPlayerId(ownedGameId, principal.getAuthenticatedUserId());
            gameInventoryService.findAllByRegisteredPlayerId(principal.getAuthenticatedUserId());
            return ResponseEntity.noContent().build();
    }

    //</editor-fold>

}
