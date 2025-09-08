package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface GhostPlayerRepository extends JpaRepository<GhostPlayer, Long> {

    GhostPlayer findByIdentifierEmail(String identifierEmailNormalized);

    boolean existsByLinkedRegisteredPlayer_Id(Long registeredPlayerId);

    GhostPlayer getReferenceByLinkedRegisteredPlayer_Id(Long registeredPlayerId);

    Set<GhostPlayer> findAllByCreator_Id(Long creatorId);
}
