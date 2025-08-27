package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// This data will be imported from Board Game Geek data

@Entity
@Table (name = "board_games")

public class Game {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "game_title", length = 255, nullable = false)
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String gameTitle;


}
