package com.mparker.playlytics.repositories;

// Imports
import com.mparker.playlytics.entities.OwnedGame;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OwnedGameRepository  extends JpaRepository<OwnedGame, Long> {

        // Custom Method to Delete OwnedGame
        void deleteByIdAndRegisteredPlayer_Id(Long id, Long playerId);

        // Custom Method to find all OwnedGames belonging to a RegisteredPlayer
        List<OwnedGame> findAllByRegisteredPlayer_Id(Long playerId);

        // Custom Method to find all OwnedGames by name, belonging to a RegisteredPlayer
        List<OwnedGame> findAllByGame_gameTitleAndRegisteredPlayer_Id(String gameName, Long playerId);

}
