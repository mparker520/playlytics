package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.dto.OwnedGameResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.entity.OwnedGame;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.exception.ExistingResourceException;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
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

    // <editor-fold desc = "Add Game to Database">

    @Transactional
    public void addBoardGame(String boardGame)  throws ExistingResourceException {

        boolean gameInDB = false;

        Game existingGame = gameRepository.getGameByGameTitleIgnoreCase(boardGame.trim().replaceAll("\\s+", " "));
        if(existingGame != null) {
            gameInDB = true;
        }

        if (!gameInDB) {
            Game game = new Game(boardGame);
            gameRepository.save(game);
        }

        else {
            throw new ExistingResourceException("There is already a game with a duplicate or similar name / title.");
        }


    }

    // </editor-fold>

}


