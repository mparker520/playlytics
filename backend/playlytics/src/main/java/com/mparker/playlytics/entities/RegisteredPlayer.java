package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "associations",
        joinColumns = @JoinColumn(name = "registered_player_id", nullable = false, updatable = false),
        inverseJoinColumns = @JoinColumn(name = "ghost_player_id"))
    @NotNull
    private Set<GhostPlayer> associations = new HashSet<>();


    @OneToOne(mappedBy = "registeredPlayer")
    private GhostPlayer ghostPlayer;

    // Inventory is Mapped via the OwnedGame Associative Entity
    // See OwnedGame.java



}
