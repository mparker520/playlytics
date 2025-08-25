package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "owned_games")
public class OwnedGame {

    // Database Columns

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // Link to User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private AppUser user;

    // Link to Game
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull
    private Game game;


}
