package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "session_participants")

public class SessionParticipant {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "result", nullable = false)
    @NotNull
    private int result;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "session_team_id", nullable = true)
    private SessionTeam sessionTeam;


    // Link to Player
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @NotNull
    private Player player;


    // Link to GamePlaySession
    // Bidirectional Mapping GamePlaySession.java
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_play_session_id", nullable = false)
    @NotNull
    private GamePlaySession gamePlaySession;


}
