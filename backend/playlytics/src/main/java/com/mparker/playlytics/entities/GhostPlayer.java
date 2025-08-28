package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.GhostStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "ghost_players")

@PrimaryKeyJoinColumn(name = "ghost_player_id")

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

    // Mapping to Linked Registered Player If Applicable: Can be Null
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_player_id",  unique = true)
    private RegisteredPlayer registeredPlayer;


    // Associations to Registered Players mapped in RegisteredPlayers.java

    // </editor-fold>

    // <editor-fold desc="Getters and Setters">

    public String getIdentifierEmail() {
        return identifierEmail;
    }

    public GhostStatus getStatus() {
        return status;
    }

    public void setStatus(GhostStatus status) {
        this.status = status;
    }

    public RegisteredPlayer getRegisteredPlayer() {
        return registeredPlayer;
    }

    public void setRegisteredPlayer(RegisteredPlayer registeredPlayer) {
        this.registeredPlayer = registeredPlayer;
    }

    // </editor-fold>

}
