package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "owned_games")
public class OwnedGame {

    // Database Columns

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CreationTimestamp
    @Column(name = "creation_timestamp", nullable = false, updatable = false)
    private Instant creationTimestamp;


    // Link to RegisteredPlayer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registered_player_id", nullable = false, updatable = false)
    @NotNull
    private RegisteredPlayer registeredPlayer;


    // Link to Game
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false, updatable = false)
    @NotNull
    private Game game;


}
