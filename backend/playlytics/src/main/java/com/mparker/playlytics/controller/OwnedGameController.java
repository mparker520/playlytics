package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.OwnedGameResponseDTO;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.GameInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
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
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(value = "gameTitle", required = false) String gameTitle)  {

        if (gameTitle == null) {
            List<OwnedGameResponseDTO> allOwnedGames = gameInventoryService.findAllByRegisteredPlayerId(principal.getAuthenticatedUserId());
            return ResponseEntity.ok(allOwnedGames);
        }

        else {
            List<OwnedGameResponseDTO> ownedGamesByName = gameInventoryService.findByRegisteredPlayerIDAndTitle(gameTitle, principal.getAuthenticatedUserId());
            return ResponseEntity.ok(ownedGamesByName);
        }

    }

    //</editor-fold>

    //<editor-fold desc = "POST Mapping">

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/owned-games/{gameId}")
    public ResponseEntity<OwnedGameResponseDTO> createOwnedGame(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("gameId") Long gameId)  {
        OwnedGameResponseDTO ownedGameResponseDTO = gameInventoryService.saveOwnedGame(gameId, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(ownedGameResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mappings">

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/owned-games/{ownedGameId}")
    public ResponseEntity<Void> deleteOwnedGame(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable ("ownedGameId") Long ownedGameId) {
        gameInventoryService.deleteByIdAndPlayerId(ownedGameId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();
    }

    //</editor-fold>

}
