package com.mparker.playlytics.repositories;

// Imports
import com.mparker.playlytics.entities.OwnedGame;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OwnedGameRepository  extends JpaRepository<OwnedGame, Long> {

        // Custom Method to Delete OwnedGame
        void deleteByIdAndRegisteredPlayer_Id(Long id, Long playerId);

}
