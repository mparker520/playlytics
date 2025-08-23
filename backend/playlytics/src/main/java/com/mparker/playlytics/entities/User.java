package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "app_users")
// User extends player and inherits id via Player's id
@PrimaryKeyJoinColumn(name= "id")

public class User extends Player {

    // Database Columns

    // Must be secured
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;


    // Maps to Player to establish a connection (Unidirectional)
    @ManyToMany
    @JoinTable(name = "connections",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> connections = new HashSet<>();


    // Maps to Games for Game Inventory (Unidirectional)
    @ManyToMany
    @JoinTable(
        name = "game_inventory",
        joinColumns = @JoinColumn(name = "player_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> ownedGames = new HashSet<>();

}
