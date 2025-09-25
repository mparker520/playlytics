package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.OwnedGame;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OwnedGameRepository  extends JpaRepository<OwnedGame, Long> {

        // Custom Method to Delete OwnedGame
        void deleteByIdAndRegisteredPlayer_Id(Long id, Long playerId);

        // Custom Method to find all OwnedGames belonging to a RegisteredPlayer
        List<OwnedGame> findAllByRegisteredPlayer_IdOrderByGameGameTitle(Long playerId);


}
