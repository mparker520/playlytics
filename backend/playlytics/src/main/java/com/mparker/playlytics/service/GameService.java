package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.exception.ExistingResourceException;
import com.mparker.playlytics.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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

        Set<Game> existingGames = new HashSet<>();
        String normalizedNewGame = boardGame.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");

        for(String word: getSignificantWords(boardGame)) {
            existingGames.addAll(gameRepository.getGamesByGameTitleContainingIgnoreCase(word));
        }

        for (Game existingGame : existingGames) {

                String normalizedExistingGame = existingGame.getGameTitle().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");


                if(normalizedExistingGame.equals(normalizedNewGame)){
                    throw new ExistingResourceException("There is already a game with a duplicate or similar name / title.");
                }

        }

        Game newGame = new Game(boardGame.trim().replaceAll("\\s+", " "));
        gameRepository.save(newGame);


    }

    // </editor-fold>


    //<editor-fold desc="Helper Methods">

    private ArrayList<String> getSignificantWords(String boardGame) {
        String[] titleWords = boardGame.split(" ");
        ArrayList<String> signifiicantWords = new ArrayList<>(0);
        Set<String> excludedWords = new HashSet<>(Arrays.asList(    "a", "an", "the", "and", "or", "but", "in", "on", "at", "to", "for",
                "of", "by", "with", "from", "this", "that", "these", "those", "it",
                "is", "are", "was", "were", "be", "been", "am", "do", "does", "did"));

        for (String word : titleWords) {
            if(!excludedWords.contains(word.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""))) {
                signifiicantWords.add(word.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""));
            }

        }

        return signifiicantWords;

    }

    //</editor-fold>


}


