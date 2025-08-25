package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "session_participants")

public class SessionParticipant {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "result", nullable = false)
    @NotNull
    private int result;



    // Link to Player
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    @NotNull
    private Player player;

    // Link to GamePlaySession
    @ManyToOne
    @JoinColumn(name = "game_play_session_id", nullable = false)
    @NotNull
    private GamePlaySession gamePlaySession;


}
