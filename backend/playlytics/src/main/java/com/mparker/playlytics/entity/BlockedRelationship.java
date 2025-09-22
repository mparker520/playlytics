package com.mparker.playlytics.entity;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "blocked_players", indexes = {
        @Index(name = "multiIndex_a_b", columnList = "blocker, blocked"),
        @Index(name = "multiIndex_b_a", columnList = "blocked, blocker")
})

public class BlockedRelationship {

    // <editor-fold desc="Database Columns">

    @EmbeddedId
    private BlockedRelationshipId id;


    // </editor-fold >


    // <editor-fold desc="Relationship Mappings">


    // Maps to RegisteredPlayer
    // If RegisteredPlayer is deleted, the BlockedRelationship is deleted
    @MapsId("blockerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blocker", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RegisteredPlayer blocker;


    // Maps to Registered Player
    // If RegisteredPlayer is deleted, the BlockedRelationship is deleted
    @MapsId("blockedId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blocked", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RegisteredPlayer blocked;


    // </editor-fold>


    // <editor-fold desc="Equals & HashCode">

    // Establish uid for comparison and hashing
    @Column (name = "uid", nullable = false, updatable = false, unique = true)
    private UUID uid = UUID.randomUUID();

    // Define Equals
    @Override
    public boolean equals(Object o) {

        // If same in memory, equal is true
        if (this == o) return true;

        // If object is null or the classes of this and object are not equal, false
        if (o == null || org.hibernate.Hibernate.getClass(this) != org.hibernate.Hibernate.getClass(o)) return false;

        // Establish object as this entity class
       BlockedRelationship that = (BlockedRelationship) o;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    // </editor-fold >


    //<editor-fold desc = "Constructor">

    public BlockedRelationship() {
    }

    public BlockedRelationship(BlockedRelationshipId id, RegisteredPlayer blocker, RegisteredPlayer blocked) {
        this.id = id;
        this.blocker = blocker;
        this.blocked = blocked;

    }

    //</editor-fold>

    //<editor-fold desc="Getters">


    public BlockedRelationshipId getId() {
        return id;
    }

    public RegisteredPlayer getBlocker() {
        return blocker;
    }

    public RegisteredPlayer getBlocked() {
        return blocked;
    }

    //</editor-fold>

}
