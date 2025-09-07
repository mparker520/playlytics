package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.OwnedGameResponseDTO;
import com.mparker.playlytics.service.GameInventoryService;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/registered-players/{registeredPlayerId}/owned-games")
    public ResponseEntity<List<OwnedGameResponseDTO>> getOwnedGames(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @RequestParam(value = "gameTitle", required = false) String gameTitle) {

        if (gameTitle == null) {
            List<OwnedGameResponseDTO> allOwnedGames = gameInventoryService.findAllByRegisteredPlayerId(registeredPlayerId);
            return ResponseEntity.ok(allOwnedGames);
        }

        else {
            List<OwnedGameResponseDTO> ownedGamesByName = gameInventoryService.findByRegisteredPlayerIDAndTitle(registeredPlayerId, gameTitle);
            return ResponseEntity.ok(ownedGamesByName);
        }

    }

    //</editor-fold>

    //<editor-fold desc = "POST Mapping">

    @PostMapping("/registered-players/{registeredPlayerId}/owned-games/{gameId}")
    public ResponseEntity<OwnedGameResponseDTO> createOwnedGame(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("gameId") Long gameId)  {
        OwnedGameResponseDTO ownedGameResponseDTO = gameInventoryService.saveOwnedGame(registeredPlayerId, gameId);
        return ResponseEntity.ok(ownedGameResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mappings">

    @DeleteMapping("/registered-players/{registeredPlayerId}/owned-games/{ownedGameId}")
    public ResponseEntity<Void> deleteOwnedGame(
            @PathVariable Long registeredPlayerId,
            @PathVariable Long ownedGameId) {
        gameInventoryService.deleteByIdAndPlayerId(ownedGameId, registeredPlayerId);
        return ResponseEntity.noContent().build();
    }

    //</editor-fold>

}
