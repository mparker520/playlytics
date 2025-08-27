package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;


@Entity
@Table(name = "connection_requests")

public class ConnectionRequest {

    // Database Columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "connection_request_status", nullable = false)
    @NotNull
    private ConnectionRequestStatus connectionRequestStatus;

    @CreationTimestamp
    @Column(name = "creation_timestamp", nullable = false, updatable = false)
    private Instant creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Instant updateTimestamp;


    // Maps to RegisteredPlayer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invite_recipient", nullable = false, updatable = false)
    @NotNull
    private RegisteredPlayer inviteRecipient;

    // Maps to RegisteredPlayer for Initiator
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_initiator", nullable = false, updatable = false)
    @NotNull
    private RegisteredPlayer initiatorId;


}
