package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface GhostPlayerRepository extends JpaRepository<GhostPlayer, Long> {

    GhostPlayer findByIdentifierEmail(String identifierEmailNormalized);

    boolean existsByLinkedRegisteredPlayer_Id(Long registeredPlayerId);

    GhostPlayer getReferenceByLinkedRegisteredPlayer_Id(Long registeredPlayerId);

    Set<GhostPlayer> findAllByCreator_Id(Long creatorId);

    GhostPlayer getReferenceByIdentifierEmail(String identifierEmail);

    boolean existsByIdentifierEmail(@Email @NotBlank String identifierEmail);


    GhostPlayer findByLinkedRegisteredPlayer_Id(Long id);

}
