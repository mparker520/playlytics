package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.GamePlaySessionDTO;
import com.mparker.playlytics.dto.GamePlaySessionResponseDTO;
import com.mparker.playlytics.service.GamePlaySessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
public class GamePlaySessionController {

    //<editor-fold desc = "Constructor">

    private final GamePlaySessionService gamePlaySessionService;

    public GamePlaySessionController(GamePlaySessionService gamePlaySessionService) {
        this.gamePlaySessionService = gamePlaySessionService;
    }

    //</editor-fold>

 //<editor-fold desc = "GET Mapping">
    @GetMapping("/registered_players/{registered_player_id}/game_play_sessions")
    public ResponseEntity<Set<GamePlaySessionResponseDTO>> getGamePlaySessions(
            @PathVariable("registered_player_id") Long registered_player_id,
            @RequestParam(value = "gameTitle", required = false) String gameTitle) {

        if (gameTitle == null) {
            Set<GamePlaySessionResponseDTO> allOwnedGames = gamePlaySessionService.findAllByPlayerId(registered_player_id);
            return ResponseEntity.ok(allOwnedGames);
        }

        else {
            Set<GamePlaySessionResponseDTO> ownedGamesByName = gamePlaySessionService.findAllByPlayerIdAndGameName(registered_player_id, gameTitle);
            return ResponseEntity.ok(ownedGamesByName);
        }

    }

    //</editor-fold>

  //<editor-fold desc = "POST Mapping">

    @PostMapping("/game_play_sessions")
    public ResponseEntity<GamePlaySessionResponseDTO> createGamePlaySession(
            @RequestBody GamePlaySessionDTO gamePlaySessionDTO)  {
        GamePlaySessionResponseDTO gamePlaySessionResponseDTO = gamePlaySessionService.assembleGpSession(gamePlaySessionDTO);
        return ResponseEntity.ok(gamePlaySessionResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mapping">

    @DeleteMapping("/game_play_sessions/{game_play_session_id}")
    public ResponseEntity<String> deleteGamePlaySession(
            @PathVariable ("game_play_session_id") Long game_play_session_id,
            // TODO: Update to auth context
            @RequestParam("registered_player_id") Long registered_player_id) {
        gamePlaySessionService.deleteByIdAndPlayerId(game_play_session_id, registered_player_id);
        return ResponseEntity.noContent().build();
    }

    //</editor-fold>

}
