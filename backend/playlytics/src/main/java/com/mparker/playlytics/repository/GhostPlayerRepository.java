package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GhostPlayerRepository extends JpaRepository<GhostPlayer, Long> {

    //<editor-fold desc="Get Ghost Player by Identifier Email">

        // Get Ghost player by identifier email
        //TODO: Possible Duplicate
        GhostPlayer findByIdentifierEmail(String identifierEmailNormalized);

        // Get Ghost player reference by identifier email
        //TODO: Possible Duplicate
        GhostPlayer getReferenceByIdentifierEmail(String identifierEmail);

        // Check if Ghost player exists by Identifier Email
        //TODO: Possible Duplicate
        boolean existsByIdentifierEmail(@Email @NotBlank String identifierEmail);


    //</editor-fold>

    //<editor-fold desc="Check existance by Linked Registered Player">

        // Check if Ghost player exists by Linked Registered Player ID to User
        //TODO: Possible Duplicate
        boolean existsByLinkedRegisteredPlayer_Id(Long registeredPlayerId);

        // Get Reference to Ghost player by Linked Registered Player ID
        //TODO: Possible Duplicate
        GhostPlayer getReferenceByLinkedRegisteredPlayer_Id(Long registeredPlayerId);

        // Get Ghost player by Linked Registered Player ID
        //TODO: Possible Duplicate
        GhostPlayer findByLinkedRegisteredPlayer_Id(Long id);


    //</editor-fold>

    // Get all Ghost Players by Creator
    // TODO: Not yet implemented
    Set<GhostPlayer> findAllByCreator_Id(Long creatorId);




}
