package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


@Entity
@Table(name = "guest_players")
@PrimaryKeyJoinColumn(name = "player_id")

public class GuestPlayer extends Player {

    // Database Columns

    @Column(name = "contact_email")
    @Email
    private String contactEmail;


}
