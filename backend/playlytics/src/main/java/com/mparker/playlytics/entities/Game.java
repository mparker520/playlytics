package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

// This data will be imported from Board Game Geek data

@Entity
@Table (name = "board_games")

public class Game {

    // <editor-fold desc="Database Columns">

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "game_title", length = 255, nullable = false, updatable = false, unique = true)
    @NotBlank
    @Size(max = 255)
    private String gameTitle;

    // </editor-fold>

    // <editor-fold desc="Equals & HashCode">

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
        Game that = (Game) o;

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
