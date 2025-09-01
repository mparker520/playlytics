package com.mparker.playlytics.entity;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "confirmed_connections", indexes = {
        @Index(name = "multiIndex_a_b", columnList = "peer_a, peer_b"),
        @Index(name = "multiIndex_b_a", columnList = "peer_b, peer_a")
})

public class ConfirmedConnection {

    // <editor-fold desc="Database Columns">

    @EmbeddedId
    private ConfirmedConnectionId id;

    @CreationTimestamp
    @Column(name = "creation_timestamp", nullable = false, updatable = false)
    @NotNull
    private Instant creationTimestamp;

    // </editor-fold >


    // <editor-fold desc="Relationship Mappings">


    // Maps to RegisteredPlayer
    // If RegisteredPlayer is deleted, the Connection is Deleted
    @MapsId("registeredPlayerAId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "peer_a", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RegisteredPlayer peerAId;


    // Maps to Registered Player
    // If RegisteredPlayer is deleted, the Connection is Deleted
    @MapsId("registeredPlayerBId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "peer_b", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RegisteredPlayer peerBId;


    // Maps to a ConnectionRequest if ConnectionRequest Status is APPROVED
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "connection_request", nullable = false, updatable = false, unique = true)
    @NotNull
    private ConnectionRequest connectionRequestId;

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
       ConfirmedConnection that = (ConfirmedConnection) o;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    // </editor-fold >


    //<editor-fold desc="Getters">

    public ConfirmedConnectionId getId() {
        return id;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public RegisteredPlayer getPeerAId() {
        return peerAId;
    }

    public RegisteredPlayer getPeerBId() {
        return peerBId;
    }

    public ConnectionRequest getConnectionRequestId() {
        return connectionRequestId;
    }


    //</editor-fold>

}
