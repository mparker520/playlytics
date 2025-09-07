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
    @GetMapping("/registered-players/{registeredPlayerId}/game-play-sessions")
    public ResponseEntity<Set<GamePlaySessionResponseDTO>> getGamePlaySessions(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @RequestParam(value = "gameTitle", required = false) String gameTitle) {

        if (gameTitle == null) {
            Set<GamePlaySessionResponseDTO> allOwnedGames = gamePlaySessionService.findAllByPlayerId(registeredPlayerId);
            return ResponseEntity.ok(allOwnedGames);
        }

        else {
            Set<GamePlaySessionResponseDTO> ownedGamesByName = gamePlaySessionService.findAllByPlayerIdAndGameName(registeredPlayerId, gameTitle);
            return ResponseEntity.ok(ownedGamesByName);
        }

    }

    //</editor-fold>

  //<editor-fold desc = "POST Mapping">

    @PostMapping("/game-play-sessions")
    public ResponseEntity<GamePlaySessionResponseDTO> createGamePlaySession(
            @RequestBody GamePlaySessionDTO gamePlaySessionDTO)  {
        GamePlaySessionResponseDTO gamePlaySessionResponseDTO = gamePlaySessionService.assembleGpSession(gamePlaySessionDTO);
        return ResponseEntity.ok(gamePlaySessionResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mapping">

    @DeleteMapping("/game-play-sessions/{sessionId}")
    public ResponseEntity<String> deleteGamePlaySession(
            @PathVariable ("sessionId") Long sessionId,
            // TODO: Update to auth context
            @RequestParam("registeredPlayerId") Long registeredPlayerId) {
        gamePlaySessionService.deleteByIdAndPlayerId(sessionId, registeredPlayerId);
        return ResponseEntity.noContent().build();
    }

    //</editor-fold>

}
