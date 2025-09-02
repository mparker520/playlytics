package com.mparker.playlytics.entity;

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
import java.util.UUID;


@Entity
@Table(name="game_play_sessions", indexes = {
        @Index(name = "ix_game_play_session_scoring_model", columnList = "scoring_model"),
        @Index(name="multiIndex_game_play_session_date_game", columnList = "session_date_time, game_id"),
        @Index(name="multiIndex_game_play_session_game_date", columnList = "game_id, session_date_time")
})

public class GamePlaySession {

    // <editor-fold desc="Database Columns">

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

    // </editor-fold >

    // <editor-fold desc="Relationship Mappings">

    // Maps to Player to Indicate who Created this GamePlaySession.
    // Require FK to Game: Can't be Null.  If RegisteredPlayer is Deleted, will update to GhostPlayer.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull
    private Player creator;


    // Set of SessionTeams in GamePlaySession
    // Bidirectional Mapping SessionTeam.Java
    // If GamePlaySession is deleted, all SessionTeams are deleted, if SessionTeam removed from set, SessionTeam removed from DB
    @OneToMany(mappedBy = "gamePlaySession", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    @NotNull
    private Set<SessionTeam> sessionTeams = new HashSet<>();


    // Set of SessionParticipants in GamePlaySession
    // Bidirectional Mapping SessionParticipant.java
    // If GamePlaySession is deleted, all SessionParticipants are deleted, if SessionParticipant removed from set, SessionParticipant removed from DB
    @OneToMany(mappedBy = "gamePlaySession", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    @NotNull
    @Size(min = 1)
    private Set<SessionParticipant> sessionParticipants = new HashSet<>();


    // Mapping to Game (Unidirectional)
    // Require FK to Game: Can't be Null.  If Game Entity is deleted, the deletion is rejected
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull
    private Game game;

    // </editor-fold >

    // <editor-fold desc="Equals and HashCode">

    // Establish uid for comparison and hashing
    @Column (name = "uid", nullable = false, updatable = false, unique = true)
    private UUID uid = UUID.randomUUID();

    // Define Equals
    @Override
    public boolean equals(Object o) {

        // If same in memory, equal is true
        if (this == o) return true;

        // If object is null or the classes of this and object are not equal, false
        if (o == null || org.hibernate.Hibernate.getClass(this) != org.hibernate.Hibernate.getClass(o)) return false;

        // Establish object as this entity class
        GamePlaySession that = (GamePlaySession) o;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    // </editor-fold>

    // <editor-fold desc="Constructors">

    public GamePlaySession() {
    }

    public GamePlaySession(Instant sessionDateTime, ScoringModel scoringModel, Player creator, Game game) {
        this.sessionDateTime = sessionDateTime;
        this.scoringModel = scoringModel;
        this.creator = creator;
        this.game = game;
    }

    // </editor-fold>

    // <editor-fold desc="Getters and Setters">

    public Long getId() {
        return id;
    }

    public Instant getSessionDateTime() {
        return sessionDateTime;
    }

    public void setSessionDateTime(Instant sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
    }

    public ScoringModel getScoringModel() {
        return scoringModel;
    }

    public void setScoringModel(ScoringModel scoringModel) {
        this.scoringModel = scoringModel;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }


    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }


    public Player getcreator() {
        return creator;
    }

    public void setcreator(Player creator) {
        this.creator = creator;
    }

    public Set<SessionTeam> getSessionTeams() {
        return sessionTeams;
    }

    public void setSessionTeams(Set<SessionTeam> sessionTeams) {
        this.sessionTeams = sessionTeams;
    }

    public Set<SessionParticipant> getSessionParticipants() {
        return sessionParticipants;
    }

    public void setSessionParticipants(Set<SessionParticipant> sessionParticipants) {
        this.sessionParticipants = sessionParticipants;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    // </editor-fold>

}
