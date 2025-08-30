package com.mparker.playlytics.services;

// Imports
import com.mparker.playlytics.entities.OwnedGame;
import com.mparker.playlytics.repositories.OwnedGameRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class GameInventoryService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final OwnedGameRepository ownedGameRepository;

    public GameInventoryService(final OwnedGameRepository ownedGameRepository) {
        this.ownedGameRepository = ownedGameRepository;
    }

    //</editor-fold>

    // <editor-fold desc = "Add OwnedGame to RegisteredPlayer Inventory">

    @Transactional
    public OwnedGame saveOwnedGame(OwnedGame ownedGame) {

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


    // Delete OwnedGame by passing in OwnedGame through Internal Validation
    @Transactional
    public void deleteOwnedGame(OwnedGame ownedGame) {

            ownedGameRepository.delete(ownedGame);

    }

    // Delete OwnedGame by OwnedGame_Id and current Player_Id
    @Transactional
    public void deleteOwnedGameByIdAndPlayerId(Long id, Long playerId) {

            ownedGameRepository.deleteByIdAndRegisteredPlayer_Id(id, playerId);

    }


    // </editor-fold>

}
