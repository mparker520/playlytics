package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "owned_games")
public class OwnedGame {

    // Database Columns

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;


    // Link to RegisteredPlayer
    @ManyToOne
    @JoinColumn(name = "registered_player_id", nullable = false)
    @NotNull
    private RegisteredPlayer registeredPlayer;

    // Link to Game
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull
    private Game game;


}
