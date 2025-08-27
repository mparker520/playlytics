package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "session_teams")

public class SessionTeam {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "result", nullable = false)
    @NotNull
    private int result;


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


}
