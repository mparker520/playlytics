package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    //<editor-fold desc = "Constructor">

    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    //</editor-fold>


    //<editor-fold desc = "GET Mappings">

        @GetMapping("/board-games")
        public ResponseEntity<Set<GameResponseDTO>> getBoardGames (
                @RequestParam(value = "databaseFilter") String title) throws NotFoundException {

                Set <GameResponseDTO> boardGameResponseDTO = gameService.findByTitle(title);

                // If no games found by filter, throw NotFoundException
                if(boardGameResponseDTO.isEmpty()) {
                    throw new NotFoundException("No games found for filter " + title);
                }

                // Else return DTO
                return ResponseEntity.ok(boardGameResponseDTO);

        }

    //</editor-fold>

    //<editor-fold desc="Add Board Game">

        @PostMapping("/board-game")
        public ResponseEntity<String> addBoardGame (
                @RequestParam(value = "boardGame") String boardGame) {

            String response = gameService.addBoardGame(boardGame);

            return ResponseEntity.ok(response);

        }

    //</editor-fold>

}
