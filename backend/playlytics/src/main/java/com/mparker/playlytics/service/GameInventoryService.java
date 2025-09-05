package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.OwnedGameDTO;
import com.mparker.playlytics.dto.OwnedGameResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.entity.OwnedGame;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.repository.GameRepository;
import com.mparker.playlytics.repository.OwnedGameRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

public class GameInventoryService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final OwnedGameRepository ownedGameRepository;
    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GameRepository gameRepository;

    public GameInventoryService(OwnedGameRepository ownedGameRepository, RegisteredPlayerRepository registeredPlayerRepository, GameRepository gameRepository) {
        this.ownedGameRepository = ownedGameRepository;
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.gameRepository = gameRepository;

    }

    //</editor-fold>

    // <editor-fold desc = "Add OwnedGame to RegisteredPlayer Inventory">

    @Transactional
    public OwnedGameResponseDTO saveOwnedGame(OwnedGameDTO ownedGameDTO) {

            // Create OwnedGame Entity
            RegisteredPlayer player = registeredPlayerRepository.getReferenceById(ownedGameDTO.playerId());
            Game game = gameRepository.getReferenceById(ownedGameDTO.gameId());

            OwnedGame ownedGame = new OwnedGame(player, game);


            // Save OwnedGame
            ownedGameRepository.save(ownedGame);


            // Create and Return GameResponseDTO
            Long gameId = ownedGame.getGame().getId();
            String gameName = ownedGame.getGame().getGameTitle();

            return  new OwnedGameResponseDTO(gameId, gameName);

    }

    // </editor-fold>

    // <editor-fold desc = "View OwnedGames in RegisteredPlayer Inventory">


    // View OwnedGames Helper Method to Create List of OwnedGameReponseDTOs

    private List<OwnedGameResponseDTO> getOwnedGamesDTOList(List<OwnedGame> ownedGamesList) {

        List<OwnedGameResponseDTO> ownedGamesResponseDTOList = new ArrayList<>();

        for (OwnedGame ownedGame : ownedGamesList) {

            Long gameId = ownedGame.getGame().getId();
            String gameName = ownedGame.getGame().getGameTitle();

            OwnedGameResponseDTO ownedGamesResponseDTO = new OwnedGameResponseDTO(gameId, gameName);
            ownedGamesResponseDTOList.add(ownedGamesResponseDTO);

        }

        return ownedGamesResponseDTOList;

    }


    // Returns all RegisteredPlayer's OwnedGames
    @Transactional(readOnly = true)
    public List<OwnedGameResponseDTO> findAllByRegisteredPlayerId(Long playerId) {

        List<OwnedGame> ownedGamesList = ownedGameRepository.findAllByRegisteredPlayer_Id(playerId);

        return getOwnedGamesDTOList(ownedGamesList);

    }


    // Returns RegisteredPlayer's OwnedGames by Game Title
    @Transactional(readOnly = true)
    public List<OwnedGameResponseDTO> findByRegisteredPlayerIDAndTitle(Long playerId, String gameName) {

        List<OwnedGame> ownedGamesByNameList = ownedGameRepository.findAllByRegisteredPlayer_IdAndGame_gameTitle(playerId, gameName);
        return getOwnedGamesDTOList(ownedGamesByNameList);

    }


    // </editor-fold>

    // <editor-fold desc = "Remove OwnedGame from RegisteredPlayer Inventory">


    // Delete OwnedGame by OwnedGame_Id and current Player_Id
    @Transactional
    public void deleteByIdAndPlayerId(Long id, Long playerId) {

            ownedGameRepository.deleteByIdAndRegisteredPlayer_Id(id, playerId);

    }


    // </editor-fold>

}
