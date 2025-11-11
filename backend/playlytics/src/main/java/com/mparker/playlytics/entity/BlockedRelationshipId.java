package com.mparker.playlytics.entity;

// Imports

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable

public class BlockedRelationshipId implements Serializable {

    // <editor-fold desc="Variable Fields">

    private Long blockerId;

    private Long blockedId;

    // </editor-fold>


    // <editor-fold desc="Equals and HashCode">

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockedRelationshipId that = (BlockedRelationshipId) o;
        return blockerId == that.blockerId && blockedId == that.blockedId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockerId, blockedId);
    }

    // </editor-fold>

    // <editor-fold desc="Constructors">

    public BlockedRelationshipId() {
    }

    public BlockedRelationshipId(Long blockerId, Long blockedId) {
        this.blockerId = blockerId;
        this.blockedId = blockedId;
    }

    // </editor-fold>

}
