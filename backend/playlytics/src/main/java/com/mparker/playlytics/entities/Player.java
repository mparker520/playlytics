package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


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

    // GamePlaySessions are Mapped via the SessionParticipant Associative Entity
    // See SessionParticipant.java


}
