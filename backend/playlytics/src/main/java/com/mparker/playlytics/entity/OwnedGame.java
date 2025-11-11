package com.mparker.playlytics.entity;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "owned_games", indexes = {
        @Index(name = "ix_owned_games_owner", columnList = "owner_id")
})
public class OwnedGame {

    // <editor-fold desc = "Database Columns">

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // </editor-fold>

    // <editor-fold desc = "Relationship Mappings">

    // Link to RegisteredPlayer
    // Deletes OwnedGame if associated RegisteredPlayer is deleted
    // Require FK to Game: Can't be Null.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private RegisteredPlayer registeredPlayer;


    // Link to Game
    // Require FK to Game: Can't be Null.  If Game Entity is deleted, the deletion is rejected
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false, updatable = false)
    @NotNull
    private Game game;

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
        OwnedGame that = (OwnedGame) o;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    // </editor-fold>

    // <editor-fold desc = "Constructors">

    public OwnedGame() {
    }

    public OwnedGame(RegisteredPlayer registeredPlayer, Game game) {
        this.registeredPlayer = registeredPlayer;
        this.game = game;
    }

    // </editor-fold>

    // <editor-fold desc="Getters and Setters">

    public Long getId() {
        return id;
    }



    public Game getGame() {
        return game;
    }


    // </editor-fold>

}
