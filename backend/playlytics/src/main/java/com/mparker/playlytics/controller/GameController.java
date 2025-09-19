package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController

public class GameController {

    //<editor-fold desc = "Constructor">

    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //</editor-fold>


    //<editor-fold desc = "Get Mappings">

    @GetMapping("/board-games")
    public ResponseEntity<Set<GameResponseDTO>> getBoardGames(
            @RequestParam(value = "databaseFilter") String title
    ) {

            Set <GameResponseDTO> boardGameResponseDTO = gameService.findByTitle(title);
            return ResponseEntity.ok(boardGameResponseDTO);

    }

    //</editor-fold>

}
