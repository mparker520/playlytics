package com.mparker.playlytics.entity;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "session_participants", indexes = {
        @Index(name = "multiIndex_session_participants_player_result", columnList = "player_id, result"),
        @Index(name = "multiIndex_session_participants_game_play_session_result", columnList = "game_play_session_id, result")
})

public class SessionParticipant {

    // <editor-fold desc = "Database Columns">

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "result", nullable = false)
    @NotNull
    @Min(0)
    private int result;

    // </editor-fold>

    // <editor-fold desc = "Relationship Mappings">

    // Link to SessionTeam: Can be Null, not all SessionParticipants are part of a team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_team_id")
    private SessionTeam sessionTeam;


    // Link to Player
    // Require FK to Player: Can't be Null.  If RegisteredPlayer is Deleted, will update to GhostPlayer.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @NotNull
    private Player player;


    // Link to GamePlaySession
    // Bidirectional Mapping GamePlaySession.java
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_play_session_id", nullable = false, updatable = false)
    @NotNull
    private GamePlaySession gamePlaySession;

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
        SessionParticipant that = (SessionParticipant) o;

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

    public SessionParticipant() {
    }

    public SessionParticipant(int result, Player player) {
        this.result = result;
        this.player = player;
    }

// </editor-fold>

    // <editor-fold desc = "Getters and Setters">

    public Long getId() {
        return id;
    }

    public void setSessionTeam(SessionTeam sessionTeam) {
        this.sessionTeam = sessionTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GamePlaySession getGamePlaySession() {
        return gamePlaySession;
    }

    public void setGamePlaySession(GamePlaySession gamePlaySession) {
        this.gamePlaySession = gamePlaySession;
    }

// </editor-fold>

}
