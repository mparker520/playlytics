package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.OwnedGameDTO;
import com.mparker.playlytics.entity.OwnedGame;
import com.mparker.playlytics.repository.GameRepository;
import com.mparker.playlytics.repository.OwnedGameRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;


@Service

public class GameInventoryService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final OwnedGameRepository ownedGameRepository;
    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GameRepository gameRepository;;

    public GameInventoryService(OwnedGameRepository ownedGameRepository, RegisteredPlayerRepository registeredPlayerRepository, GameRepository gameRepository) {
        this.ownedGameRepository = ownedGameRepository;
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.gameRepository = gameRepository;

    }

    //</editor-fold>

    // <editor-fold desc = "Add OwnedGame to RegisteredPlayer Inventory">

    @Transactional
    public OwnedGame saveOwnedGame(OwnedGameDTO ownedGameDTO) {

            OwnedGame ownedGame = new OwnedGame();
            ownedGame.setGame(gameRepository.getReferenceById(ownedGameDTO.gameId()));
            ownedGame.setRegisteredPlayer(registeredPlayerRepository.getReferenceById(ownedGameDTO.playerId()));

            return ownedGameRepository.save(ownedGame);

    }

    // </editor-fold>

    // <editor-fold desc = "View OwnedGames in RegisteredPlayer Inventory">

    // Returns all RegisteredPlayer's OwnedGames
    @Transactional(readOnly = true)
    public List<OwnedGame> getAllOwnedGamesByPlayerId(Long playerId) {
        return ownedGameRepository.findAllByRegisteredPlayer_Id(playerId);
    }

    @Transactional(readOnly = true)
    // Returns RegisteredPlayer's OwnedGames by Name
    public List<OwnedGame> findOwnedGameByNameAndPlayerId(String gameName, Long playerId) {
        return ownedGameRepository.findAllByGame_gameTitleAndRegisteredPlayer_Id(gameName, playerId);
    }


    // </editor-fold>

    // <editor-fold desc = "Remove OwnedGame from RegisteredPlayer Inventory">


    // Delete OwnedGame by OwnedGame_Id and current Player_Id
    @Transactional
    public void deleteOwnedGameByIdAndPlayerId(Long id, Long playerId) {

            ownedGameRepository.deleteByIdAndRegisteredPlayer_Id(id, playerId);

    }


    // </editor-fold>

}
