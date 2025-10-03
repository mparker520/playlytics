package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisteredPlayerRepository extends JpaRepository<RegisteredPlayer, Long> {

    boolean existsByIdAndAssociations(Long registeredPlayerId, GhostPlayer ghostPlayer);


    List<RegisteredPlayer> findAllByIdNot(Long id);

    RegisteredPlayer getReferenceByLoginEmailOrDisplayName(String loginEmail, String displayName);

    boolean existsByLoginEmail(String username);

    RegisteredPlayer getReferenceByLoginEmail(String username);

    boolean existsByDisplayName(String displayName);

}
