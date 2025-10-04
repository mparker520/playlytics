package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisteredPlayerRepository extends JpaRepository<RegisteredPlayer, Long> {

    // TODO: Check if this needs fix
    // Does Association Exist with Ghost Player
    boolean existsByIdAndAssociations(Long registeredPlayerId, GhostPlayer ghostPlayer);

    // Get Reference to Registered Player by Login Email or Display name
    RegisteredPlayer getReferenceByLoginEmailOrDisplayName(String loginEmail, String displayName);

    // Exists by Login Email
    boolean existsByLoginEmail(String username);

    // Get Reference to Registerd Player by Login Email
    //TODO: Does this duplicate getReferenceByLoginEmailOrDisplayName()?
    RegisteredPlayer getReferenceByLoginEmail(String username);

    // Checks if Exists by Display name
    boolean existsByDisplayName(String displayName);

}
