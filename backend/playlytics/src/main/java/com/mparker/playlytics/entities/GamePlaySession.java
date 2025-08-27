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
import java.util.UUID;


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


    // Equals and HashCode Override Methods

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


}
