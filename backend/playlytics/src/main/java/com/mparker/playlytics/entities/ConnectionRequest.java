package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import com.sun.net.httpserver.Request;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Entity
@Table(name = "connection_requests")

public class ConnectionRequest {

    // Database Columns

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "connection_request_status", nullable = false)
    @NotNull
    private ConnectionRequestStatus connectionRequestStatus;

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;


    // Maps to RegisteredPlayer
    @ManyToOne
    @JoinColumn(name = "invite_recipient", nullable = false)
    @NotNull
    private RegisteredPlayer inviteRecipient;

    // Maps to RegisteredPlayer for Initiator
    @ManyToOne
    @JoinColumn(name = "request_initiator", nullable = false)
    @NotNull
    private RegisteredPlayer initiatorId;



}
