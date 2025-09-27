package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.GamePlaySessionDTO;
import com.mparker.playlytics.dto.GamePlaySessionResponseDTO;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.GamePlaySessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import java.util.Set;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GamePlaySessionController {

    //<editor-fold desc = "Constructor">

    private final GamePlaySessionService gamePlaySessionService;

    public GamePlaySessionController(GamePlaySessionService gamePlaySessionService) {
        this.gamePlaySessionService = gamePlaySessionService;
    }

    //</editor-fold>

//<editor-fold desc = "GET Mapping">

//<editor-fold desc="Get Game Play Sessions for Player">
@PreAuthorize("isAuthenticated()")    @GetMapping("/game-play-sessions")
    public ResponseEntity<Set<GamePlaySessionResponseDTO>> getGamePlaySessions(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(value = "gameTitle", required = false) String gameTitle) {

        if (gameTitle == null) {
            Set<GamePlaySessionResponseDTO> allGamePlaySessions = gamePlaySessionService.findAllByPlayerId(principal.getAuthenticatedUserId());
            return ResponseEntity.ok(allGamePlaySessions);
        }

        else {
            Set<GamePlaySessionResponseDTO> allGamePlaySessionsByTitle = gamePlaySessionService.findAllByGameName(gameTitle, principal.getAuthenticatedUserId());
            return ResponseEntity.ok(allGamePlaySessionsByTitle);
        }

    }
    //</editor-fold>


    //<editor-fold desc="Get Pending Game Play Sessions for Player">
    @PreAuthorize("isAuthenticated()")    @GetMapping("/pending-game-play-sessions")
    public ResponseEntity<Set<GamePlaySessionResponseDTO>> getPendingGamePlaySessions(
            @AuthenticationPrincipal CustomUserDetails principal) {

            Set<GamePlaySessionResponseDTO> allPendingGamePlaySessions = gamePlaySessionService.getAllPendingGpSessions(principal.getAuthenticatedUserId());
            return ResponseEntity.ok(allPendingGamePlaySessions);


    }
    //</editor-fold>


    //</editor-fold>

 //<editor-fold desc = "POST Mapping">

    @PostMapping("/game-play-sessions")
    public ResponseEntity<GamePlaySessionResponseDTO> createGamePlaySession(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody GamePlaySessionDTO gamePlaySessionDTO)  {

        GamePlaySessionResponseDTO gamePlaySessionResponseDTO = gamePlaySessionService.assembleGpSession(gamePlaySessionDTO, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(gamePlaySessionResponseDTO);

    }

    //</editor-fold>

 //<editor-fold desc = "DELETE Mapping">

    @DeleteMapping("/game-play-sessions/{sessionId}")
    public ResponseEntity<String> deleteGamePlaySession(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable ("sessionId") Long sessionId) {

        gamePlaySessionService.deleteByIdAndPlayerId(sessionId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();

    }

    //</editor-fold>

}
