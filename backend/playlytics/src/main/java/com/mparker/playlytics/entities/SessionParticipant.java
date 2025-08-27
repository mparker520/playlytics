package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "session_participants")

public class SessionParticipant {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "result", nullable = false)
    @NotNull
    @Min(0)
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
    @JoinColumn(name = "game_play_session_id", nullable = false, updatable = false)
    @NotNull
    private GamePlaySession gamePlaySession;


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
        SessionParticipant that = (SessionParticipant) o;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

}
