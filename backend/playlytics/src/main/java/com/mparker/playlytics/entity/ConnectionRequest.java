package com.mparker.playlytics.entity;

// Imports
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "connection_requests", indexes = {
        @Index(name = "ix_connection_request_status", columnList = "connection_request_status"),
        @Index(name="multiIndex_status_recipient", columnList = "connection_request_status, request_recipient"),
        @Index(name="ix_connection_request_initiator", columnList =  "request_initiator"),
        @Index(name = "ix_connection_request_recipient", columnList = "request_recipient")
})

public class ConnectionRequest {

    // <editor-fold desc="Database Columns">

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // </editor-fold >


    // <editor-fold desc="Relationship Mappings">

    // Maps to RegisteredPlayer
    // Connection Request is Deleted if the Invite Recipient is Deleted
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invite_recipient", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RegisteredPlayer inviteRecipient;

    // Maps to RegisteredPlayer for Initiator
    // Connection Request is Deleted if the Invitor is Deleted
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_initiator", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RegisteredPlayer initiatorId;

    // </editor-fold >


    // <editor-fold desc="Equals and HashCode">

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
        ConnectionRequest that = (ConnectionRequest) o;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    // </editor-fold >

    // <editor-fold desc="Getters and Setter">

    public Long getId() {
        return id;
    }

    public ConnectionRequestStatus getConnectionRequestStatus() {
        return connectionRequestStatus;
    }

    public void setConnectionRequestStatus(ConnectionRequestStatus connectionRequestStatus) {
        this.connectionRequestStatus = connectionRequestStatus;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public RegisteredPlayer getInviteRecipient() {
        return inviteRecipient;
    }

    public RegisteredPlayer getInitiatorId() {
        return initiatorId;
    }


    // </editor-fold>

}
