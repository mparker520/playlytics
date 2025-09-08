package com.mparker.playlytics.entity;

// Imports
import com.mparker.playlytics.enums.GhostStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "ghost_players")

@PrimaryKeyJoinColumn(name = "id")

public class GhostPlayer extends Player {

    // <editor-fold desc="Database Columns">

    @Column(name = "identifier_email", nullable = false, unique = true)
    @Email
    @NotBlank
    private String identifierEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GhostStatus status;

    // </editor-fold>

    // <editor-fold desc="Relationship Mapping">

    // Mapping to Linked Registered Player If Applicable: Can be Null
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_registered_player_id",  unique = true)
    private RegisteredPlayer linkedRegisteredPlayer;

    // Mapping to Creator

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private RegisteredPlayer creator;

    // Associations to Registered Players mapped in RegisteredPlayers.java


    // </editor-fold>

    //<editor-fold desc = "Constructors">

    public GhostPlayer() {
    }

    public GhostPlayer(String firstName, String lastName, byte[] avatar, String identifierEmail, GhostStatus status, RegisteredPlayer registeredPlayer, RegisteredPlayer creator) {

        super(firstName, lastName, avatar);

        this.identifierEmail = identifierEmail;
        this.status = status;
        this.linkedRegisteredPlayer = registeredPlayer;
        this.creator = creator;
    }

    //</editor-fold>

    // <editor-fold desc="Getters and Setters">

    public String getIdentifierEmail() {
        return identifierEmail;
    }

    public void setIdentifierEmail(String identifierEmail) {
        this.identifierEmail = identifierEmail;
    }

    public GhostStatus getStatus() {
        return status;
    }

    public void setStatus(GhostStatus status) {
        this.status = status;
    }

    public RegisteredPlayer getLinkedRegisteredPlayer() {
        return linkedRegisteredPlayer;
    }

    public void setLinkedRegisteredPlayer(RegisteredPlayer linkedRegisteredPlayer) {
        this.linkedRegisteredPlayer = linkedRegisteredPlayer;
    }

    public RegisteredPlayer getCreator() {
        return creator;
    }

    public void setCreator(RegisteredPlayer creator) {
        this.creator = creator;
    }

    // </editor-fold>

}
