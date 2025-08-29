package com.mparker.playlytics.services;

// Imports
import com.mparker.playlytics.entities.OwnedGame;
import com.mparker.playlytics.repositories.OwnedGameRepository;
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

        public OwnedGame saveOwnedGame(OwnedGame ownedGame) {
            return ownedGameRepository.save(ownedGame);
        }

    // </editor-fold>


    // <editor-fold desc = "Remove Game from RegisteredPlayer Inventory">

        public void deleteOwnedGame(OwnedGame ownedGame) {

            if (ownedGame != null) {
                    ownedGameRepository.delete(ownedGame);
            }
        }


    public void deleteByIdAndPlayerId(Long id, Long playerId) {

        if (id != null && playerId != null) {
            ownedGameRepository.deleteByIdAndRegisteredPlayer_Id(id, playerId);
        }
    }



    // </editor-fold>


    // <editor-fold desc = "View Games in RegisteredPlayer Inventory">



    // </editor-fold>

}
