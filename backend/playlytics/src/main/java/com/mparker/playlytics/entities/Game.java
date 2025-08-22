package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Game {
    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

}
