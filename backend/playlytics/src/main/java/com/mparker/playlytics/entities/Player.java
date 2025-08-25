package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
// Player is joined to User through Inheritance via Player id being inherited as User id
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="players")

public class Player {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "display_name", nullable = false)
    @NotNull
    private String displayName;

    @Lob
    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;

    @Column(name = "registration_status")
    private boolean registrationStatus;

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;


    // Maps to RegisteredPlayer to Indicate who Created this Player
    @ManyToOne
    @JoinColumn(name = "created_by")
    private RegisteredPlayer creatorId;

    // GamePlaySessions are Mapped via the SessionParticipant Associative Entity
    // See SessionParticipant.java


}
