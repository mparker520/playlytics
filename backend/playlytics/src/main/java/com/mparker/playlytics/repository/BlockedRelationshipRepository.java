package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.entity.BlockedRelationship;
import com.mparker.playlytics.entity.BlockedRelationshipId;
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedRelationshipRepository extends JpaRepository<BlockedRelationship, BlockedRelationshipId> {

    boolean existsByBlockerAndBlockedOrBlockerAndBlocked(RegisteredPlayer sender, RegisteredPlayer recipient, RegisteredPlayer recipient1, RegisteredPlayer sender1);

}
