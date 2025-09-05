package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.OwnedGameResponseDTO;
import com.mparker.playlytics.service.GameInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OwnedGameController {

    private final GameInventoryService gameInventoryService;

    public OwnedGameController(GameInventoryService gameInventoryService) {
        this.gameInventoryService = gameInventoryService;
    }

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

    @DeleteMapping("/registered_players/{registered_player_id}/owned_games/{owned_game_id}")
    public ResponseEntity<String> deleteOwnedGame(
            @PathVariable Long registered_player_id,
            @PathVariable Long owned_game_id) {
        gameInventoryService.deleteByIdAndPlayerId(owned_game_id, registered_player_id);
        return ResponseEntity.noContent().build();
    }


}
