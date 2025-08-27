package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "registered_players")
// User extends player and inherits id via Player's id
@PrimaryKeyJoinColumn(name= "registered_player_id")

public class RegisteredPlayer extends Player {

    // Database Columns

    @Column(name = "display_name", nullable = false, length = 255, unique = true)
    @NotBlank
    @Size(max = 255)
    private String displayName;

    @Column(name = "login_email", nullable = false, unique = true)
    @NotNull
    @Email
    private String loginEmail;

    // Must be secured
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    // Inventory is Mapped via the OwnedGame Associative Entity
    // See OwnedGame.java



    @PrePersist
    @PreUpdate
    public void stripInputFields() {

        super.stripInputFields();

        if (loginEmail != null) {
            this.loginEmail = loginEmail.strip();
        }

        if (displayName != null) {
            this.displayName = displayName.strip();
        }

    }


}
