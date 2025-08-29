package com.mparker.playlytics.services;

// Imports
import com.mparker.playlytics.entities.OwnedGame;
import com.mparker.playlytics.repositories.OwnedGameRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


@Service

public class GameInventoryService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final OwnedGameRepository ownedGameRepository;

    public GameInventoryService(final OwnedGameRepository ownedGameRepository) {
        this.ownedGameRepository = ownedGameRepository;
    }

    //</editor-fold>

    // <editor-fold desc = "Add Game to RegisteredPlayer Inventory">

    @Transactional
    public OwnedGame saveOwnedGame(OwnedGame ownedGame) {

            return ownedGameRepository.save(ownedGame);

    }

    // </editor-fold>

    // <editor-fold desc = "Remove Game from RegisteredPlayer Inventory">


    // Delete OwnedGame by passing in OwnedGame
    @Transactional
    public void deleteOwnedGame(OwnedGame ownedGame) {

        if (ownedGame != null) {
                ownedGameRepository.delete(ownedGame);
        }

    }

    // Delete OwnedGame by OwnedGame_Id and current Player_Id
    @Transactional
    public void deleteOwnedGameByIdAndPlayerId(Long id, Long playerId) {

        if (id != null && playerId != null) {
            ownedGameRepository.deleteByIdAndRegisteredPlayer_Id(id, playerId);
        }

    }


    // </editor-fold>

    // <editor-fold desc = "View Games in RegisteredPlayer Inventory">

    // Returns all RegisteredPlayer's OwnedGames
    @Transactional(readOnly = true)
    public Iterable<OwnedGame> getAllOwnedGamesByPlayerId(Long playerId) {
        return ownedGameRepository.findAllByRegisteredPlayer_Id(playerId);
    }

    @Transactional(readOnly = true)
    // Returns RegisteredPlayer's OwnedGames by Name
    public Iterable<OwnedGame> getOwnedGameByNameAndPlayerId(String gameName, Long playerId) {
        return ownedGameRepository.findAllByGame_gameTitleAndRegisteredPlayer_Id(gameName, playerId);
    }


    // </editor-fold>

}
