package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.composite_ids.ConfirmedConnectionId;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "confirmed_connections")

public class ConfirmedConnection {

    // Database Columns

    @EmbeddedId
    private ConfirmedConnectionId id;

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;
    

    // Maps to RegisteredPlayer
    @ManyToOne
    @JoinColumn(name = "peer_a")
    private RegisteredPlayer peerAId;

    // Maps to Registered Player
    @ManyToOne
    @JoinColumn(name = "peer_b")
    private RegisteredPlayer peerBId;

    // Maps to a ConnectionRequest if ConnectionRequest Status is APPROVED
    @OneToOne
    @JoinColumn(name = "connection_request")
    private ConnectionRequest connectionRequestId;

}
