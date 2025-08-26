package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "session_teams")

public class SessionTeam {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "result", nullable = false)
    @NotNull
    private int result;


    // Bidirectional Mapping to SessionParticipants
    @OneToMany
    @JoinColumn(name = "members")
    private Set<SessionParticipant> members = new HashSet<>();


    // Maps to GamePlaySession
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_play_session_id", nullable = false)
    @NotNull
    private GamePlaySession gamePlaySession;


}
