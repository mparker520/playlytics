package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="play_sessions")

public class PlaySession {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "session_date_time", nullable = false)
    @NotNull
    private Instant sessionDateTime;


    // Mapping to Players (See Bidirectional mapping at Player.java 24-28)
    @ManyToMany(mappedBy = "playSessions")
    @NotNull
    private Set<Player> players = new HashSet<Player>();


    // Mapping to Game (Unidirectional)
    @ManyToOne
    @JoinColumn(name = "game_Id", nullable = false)
    @NotNull
    private Game game;

}
