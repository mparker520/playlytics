package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import java.time.Instant;


@Entity
@Table(name="play_sessions")
public class PlaySession {
    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "session_date_time", nullable = false)
    private Instant sessionDateTime;

    @ManyToOne
    @JoinColumn(name = "game_Id")
    private Game game;

}
