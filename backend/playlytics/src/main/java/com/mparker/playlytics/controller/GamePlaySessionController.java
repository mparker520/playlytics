package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.service.GameInventoryService;
import com.mparker.playlytics.service.GamePlaySessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class GamePlaySessionController {

    //<editor-fold desc = "Constructor">

    private final GamePlaySessionService gamePlaySessionService;

    public GamePlaySessionController(GamePlaySessionService gamePlaySessionService) {
        this.gamePlaySessionService = gamePlaySessionService;
    }

    //</editor-fold>

   /* //<editor-fold desc = "GET Mapping">
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

    //</editor-fold> */

    //<editor-fold desc = "DELETE Mapping">

    @DeleteMapping("/registered_players/{registered_player_id}/game_play_sessions/{game_play_session_id}")
    public ResponseEntity<String> deleteGamePlaySession(
            @PathVariable Long registered_player_id,
            @PathVariable Long game_play_session_id) {
        gamePlaySessionService.deleteByIdAndPlayerId(game_play_session_id, registered_player_id);
        return ResponseEntity.noContent().build();
    }

    //</editor-fold>

}
