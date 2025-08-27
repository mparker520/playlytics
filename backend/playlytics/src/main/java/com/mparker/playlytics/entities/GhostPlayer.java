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

    // Database Columns

    @Column(name = "identifier_email", nullable = false, unique = true)
    @Email
    @NotBlank
    private String identifierEmail;

    @Column(name = "status", nullable = false)
    private GhostStatus status;


    @PrePersist
    @PreUpdate
    public void stripInputFields() {

        if (identifierEmail != null) {
            this.identifierEmail = identifierEmail.strip();
        }
    }


}
