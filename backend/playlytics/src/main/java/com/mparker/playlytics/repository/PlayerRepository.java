package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    // TODO: Is this needed?
    Player getById(Long id);

}
