package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.ScoringModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="play_sessions")

public class GamePlaySession {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "session_date_time", nullable = false)
    @NotNull
    private Instant sessionDateTime;

    @Column(name = "scoring_model", nullable = false)
    @NotNull
    private ScoringModel scoringModel;


    // Set of SessionParticipants in GamePlaySession
    @OneToMany(mappedBy = "gamePlaySession")
    private Set<SessionParticipant> sessionParticipants = new HashSet<>();


    // Mapping to Game (Unidirectional)
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull
    private Game game;

}
