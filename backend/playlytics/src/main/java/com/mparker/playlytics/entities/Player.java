package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;


@Entity
// Player is joined to User through Inheritance via Player id being inherited as User id
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="players")

public class Player {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "email", nullable = false)
    @Email
    @NotNull
    private String email;

    @Column(name = "display_name", nullable = false)
    @NotNull
    private String displayName;


    // Maps to Play Sessions (See Bidirectional mapping at PlaySession.java 22-23)
    @ManyToMany
    @JoinTable(name = "game_players",
        joinColumns = @JoinColumn(name = "player_id"),
        inverseJoinColumns = @JoinColumn(name= "play_session_id")
    )
    private Set<PlaySession> playSessions = new HashSet<>();

}
