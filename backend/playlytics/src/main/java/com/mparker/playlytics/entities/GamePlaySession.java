package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.ScoringModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="play_sessions")

public class GamePlaySession {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "session_date_time", nullable = false)
    @PastOrPresent
    @NotNull
    private Instant sessionDateTime;

    @Column(name = "scoring_model", nullable = false)
    @NotNull
    private ScoringModel scoringModel;

    @CreationTimestamp
    @Column(name = "creation_timestamp", updatable = false)
    private Instant creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Instant updateTimestamp;


    // Maps to RegisteredPlayer to Indicate who Created this GamePlaySession
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @NotNull
    private RegisteredPlayer creatorId;


    /* Maps to RegisteredPlayer to Indicate who Last Updated this GamePlaySession
    THIS ENTIRE COLUMN IS WEIRD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_by")
    private RegisteredPlayer updaterId; */


    // Set of SessionTeams in GamePlaySession
    // Bidirectional Mapping SessionTeam.Java
    @OneToMany(mappedBy = "gamePlaySession")
    @NotNull
    private Set<SessionTeam> sessionTeams = new HashSet<>();


    // Set of SessionParticipants in GamePlaySession
    // Bidirectional Mapping SessionParticipant.java
    @OneToMany(mappedBy = "gamePlaySession")
    @NotNull
    @Size(min = 1)
    private Set<SessionParticipant> sessionParticipants = new HashSet<>();


    // Mapping to Game (Unidirectional)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull
    private Game game;


}
