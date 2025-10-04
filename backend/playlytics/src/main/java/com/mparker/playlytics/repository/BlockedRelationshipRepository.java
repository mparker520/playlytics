package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.entity.BlockedRelationship;
import com.mparker.playlytics.entity.BlockedRelationshipId;
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BlockedRelationshipRepository extends JpaRepository<BlockedRelationship, BlockedRelationshipId> {

    // Check if block exists by user and peer or peer and user
    boolean existsByBlockerAndBlockedOrBlockerAndBlocked(RegisteredPlayer sender, RegisteredPlayer recipient, RegisteredPlayer recipient1, RegisteredPlayer sender1);

    // Get all blockers for user
    Set<BlockedRelationship> findAllByBlocker_Id(Long authUserId);

}
