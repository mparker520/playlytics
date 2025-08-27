package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "session_teams", indexes = {
        @Index(name = "multiIndex_session_teams_game_play_session_rank", columnList = "game_play_session_id, result"),
})

public class SessionTeam {

    // <editor-fold desc = "Database Columns">

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "result", nullable = false)
    @NotNull
    private int result;

    // </editor-fold>

    // <editor-fold desc = "Relationship Mappings">

    // Bidirectional Mapping to SessionParticipants
    @OneToMany (mappedBy =  "sessionTeam")
    @NotNull
    @Size(min = 2)
    private Set<SessionParticipant> teamMembers = new HashSet<>();


    // Maps to GamePlaySession
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_play_session_id", nullable = false)
    @NotNull
    private GamePlaySession gamePlaySession;

    // </editor-fold>

    // <editor-fold desc = "Equals and HashCode">

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
        SessionTeam that = (SessionTeam) o;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    // </editor-fold>

}
