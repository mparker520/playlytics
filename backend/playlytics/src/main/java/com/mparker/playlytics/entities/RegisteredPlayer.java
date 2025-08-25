package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "registered_players")
// User extends player and inherits id via Player's id
@PrimaryKeyJoinColumn(name= "player_id")

public class RegisteredPlayer extends Player {

    // Database Columns

    @Column(name = "login_email", nullable = false)
    @NotNull
    @Email
    private String loginEmail;

    // Must be secured
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    // Inventory is Mapped via the OwnedGame Associative Entity
    // See OwnedGame.java


}
