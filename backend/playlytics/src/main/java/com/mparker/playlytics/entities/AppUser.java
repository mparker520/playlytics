package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "app_users")
// User extends player and inherits id via Player's id
@PrimaryKeyJoinColumn(name= "id")

public class AppUser extends Player {

    // Database Columns

    // Must be secured
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    // Inventory is Mapped via the OwnedGame Associative Entity
    // See OwnedGame.java


}
