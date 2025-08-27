package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.composite_ids.ConfirmedConnectionId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "confirmed_connections")

public class ConfirmedConnection {

    // Database Columns

    @EmbeddedId
    private ConfirmedConnectionId id;

    @CreationTimestamp
    @Column(name = "creation_timestamp", nullable = false, updatable = false)
    @NotNull
    private Instant creationTimestamp;


    // Maps to RegisteredPlayer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "peer_a", nullable = false, updatable = false)
    @NotNull
    private RegisteredPlayer peerAId;

    // Maps to Registered Player
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "peer_b", nullable = false, updatable = false)
    @NotNull
    private RegisteredPlayer peerBId;

    // Maps to a ConnectionRequest if ConnectionRequest Status is APPROVED
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "connection_request", nullable = false, updatable = false, unique = true)
    @NotNull
    private ConnectionRequest connectionRequestId;


}
