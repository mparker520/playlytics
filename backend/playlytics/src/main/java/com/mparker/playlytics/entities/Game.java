package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

// This data will be imported from Board Game Geek data
// id will be imported, not auto-generated

@Entity
@Table (name = "board_games")

public class Game {

    // Database Columns
    @Id
    private int id;

    @Column(name = "game_title", nullable = false, unique = true)
    @NotNull
    private String gameTitle;


}
