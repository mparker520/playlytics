package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.ScoringModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
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

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;

    // Maps to RegisteredPlayer to Indicate who Created this GamePlaySession
    @ManyToOne
    @JoinColumn(name = "created_by")
    private RegisteredPlayer creatorId;

    // Maps to RegisteredPlayer to Indicate who Created this GamePlaySession
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private RegisteredPlayer updaterId;


    // Set of SessionTeams in GamePlaySession
    // Bidirectional Mapping @ Lines 23-26 of SessionTeam.Java
    @OneToMany(mappedBy = "gamePlaySession")
    private Set<SessionTeam> sessionTeams = new HashSet<>();


    // Set of SessionParticipants in GamePlaySession
    // Bidirectional Mapping @ Lines 40-43 of SessionParticipant.java
    @OneToMany(mappedBy = "gamePlaySession")
    private Set<SessionParticipant> sessionParticipants = new HashSet<>();


    // Mapping to Game (Unidirectional)
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull
    private Game game;


}
