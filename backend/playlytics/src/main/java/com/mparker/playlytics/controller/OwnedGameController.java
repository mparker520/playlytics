package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.service.GameInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OwnedGameController {

    private final GameInventoryService gameInventoryService;

    public OwnedGameController(GameInventoryService gameInventoryService) {
        this.gameInventoryService = gameInventoryService;
    }

    @DeleteMapping("/registered_players/{registered_player_id}/ownedGame/{ownedGameId}")
    public ResponseEntity<String> deleteOwnedGame(@PathVariable long registered_player_id, @PathVariable long ownedGameId) {
        gameInventoryService.deleteByIdAndPlayerId(ownedGameId, registered_player_id);
        return ResponseEntity.noContent().build();
    }


}
