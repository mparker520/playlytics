package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.OwnedGame;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OwnedGameRepository  extends JpaRepository<OwnedGame, Long> {

        // Delete OwnedGame by id and registered player id
        void deleteByIdAndRegisteredPlayer_Id(Long id, Long playerId);

        // Find all OwnedGames belonging to a RegisteredPlayer
        List<OwnedGame> findAllByRegisteredPlayer_IdOrderByGameGameTitle(Long playerId);


}
