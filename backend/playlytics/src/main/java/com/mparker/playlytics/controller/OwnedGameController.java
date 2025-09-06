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
    @GetMapping("/registered_players/{registered_player_id}/owned_games")
    public ResponseEntity<List<OwnedGameResponseDTO>> getOwnedGames(
            @PathVariable("registered_player_id") Long registered_player_id,
            @RequestParam(value = "gameTitle", required = false) String gameTitle) {

        if (gameTitle == null) {
            List<OwnedGameResponseDTO> allOwnedGames = gameInventoryService.findAllByRegisteredPlayerId(registered_player_id);
            return ResponseEntity.ok(allOwnedGames);
        }

        else {
            List<OwnedGameResponseDTO> ownedGamesByName = gameInventoryService.findByRegisteredPlayerIDAndTitle(registered_player_id, gameTitle);
            return ResponseEntity.ok(ownedGamesByName);
        }

    }

    //</editor-fold>

    //<editor-fold desc = "POST Mapping">

    @PostMapping("/registered_players/{registered_player_id}/owned_games")
    public ResponseEntity<OwnedGameResponseDTO> createOwnedGame(
            @PathVariable("registered_player_id") Long registered_player_id,
            @RequestBody OwnedGameDTO ownedGameDTO)  {
        OwnedGameResponseDTO ownedGameResponseDTO = gameInventoryService.saveOwnedGame(registered_player_id, ownedGameDTO);
        return ResponseEntity.ok(ownedGameResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mappings">

    @DeleteMapping("/registered_players/{registered_player_id}/owned_games/{owned_game_id}")
    public ResponseEntity<String> deleteOwnedGame(
            @PathVariable Long registered_player_id,
            @PathVariable Long owned_game_id) {
        gameInventoryService.deleteByIdAndPlayerId(owned_game_id, registered_player_id);
        return ResponseEntity.noContent().build();
    }

    //</editor-fold>

}
