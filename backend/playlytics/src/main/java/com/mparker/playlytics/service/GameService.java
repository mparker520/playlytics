package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service

public class GameService {

    //<editor-fold desc = "Constructor">

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
}

//</editor-fold>

    //<editor-fold desc = "Get All Board Games By Title">

        public Set<GameResponseDTO> findByTitle(String title) {

            Set <Game> gameSet = gameRepository.findByGameTitleStartingWithOrderByGameTitle(title);
            Set<GameResponseDTO> gameResponseDTOSet = new HashSet<>();

            if (gameSet.isEmpty()) {
                gameSet = gameRepository.findByGameTitleContainingIgnoreCase(title);
            }


            for (Game game : gameSet) {

                GameResponseDTO gameResponseDTO = new GameResponseDTO(game.getId(), game.getGameTitle());
                gameResponseDTOSet.add(gameResponseDTO);

            }

            return  gameResponseDTOSet;

        }

    //</editor-fold>

    public String addBoardGame(String boardGame) {
        return boardGame;
    }

}


