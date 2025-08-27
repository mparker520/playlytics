package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.GhostStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "guest_players")

@PrimaryKeyJoinColumn(name = "guest_player_id")

public class GhostPlayer extends Player {

    // <editor-fold desc="Database Columns">

    @Column(name = "identifier_email", nullable = false, unique = true)
    @Email
    @NotBlank
    private String identifierEmail;

    @Column(name = "status", nullable = false)
    private GhostStatus status;

    // </editor-fold>

    // <editor-fold desc="Relationship Mapping">

    // Mapping to Linked Registered Player If Applicable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_player_id", updatable = false, unique = true)
    private RegisteredPlayer registeredPlayer;


    // Associations to Registered Players mapped in RegisteredPlayers.java

    // </editor-fold>

}
