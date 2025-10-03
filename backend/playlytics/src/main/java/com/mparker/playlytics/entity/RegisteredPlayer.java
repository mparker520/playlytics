package com.mparker.playlytics.entity;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "registered_players", indexes = {
        @Index(name = "ix_registered_players_display_name", columnList = "display_name"),
        @Index(name = "ix_registered_players_login_email", columnList = "login_email")
})

// User extends player and inherits id via Player's id
@PrimaryKeyJoinColumn(name= "id")

public class RegisteredPlayer extends Player {

    // <editor-fold desc = "Database Columns">

    @Column(name = "display_name", nullable = false, length = 255, unique = true)
    @NotBlank
    @Size(max = 255)
    private String displayName;

    @Column(name = "login_email", nullable = false, unique = true)
    @NotNull
    @Email
    private String loginEmail;

    // Must be secured
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    // </editor-fold>

    // <editor-fold desc = "Relationship Mappings">

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "associations",
        joinColumns = @JoinColumn(name = "registered_player_id"),
        inverseJoinColumns = @JoinColumn(name = "ghost_player_id"),
        uniqueConstraints = @UniqueConstraint(name = "ux_associations_registered_ghost",
                columnNames = {"registered_player_id", "ghost_player_id"}))
    private Set<GhostPlayer> associations = new HashSet<>();


    @OneToOne(mappedBy = "linkedRegisteredPlayer")
    private GhostPlayer ghostPlayer;

    // Inventory is Mapped via the OwnedGame Associative Entity
    // See OwnedGame.java

    // </editor-fold>

    public RegisteredPlayer(String firstName, String lastName, byte[] avatar, String displayName, String loginEmail, String password) {
        super(firstName, lastName, avatar);
        this.displayName = displayName;
        this.loginEmail = loginEmail;
        this.password = password;
    }

    public RegisteredPlayer() {
    }

    // <editor-fold desc = "Getters and Setters">


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public Set<GhostPlayer> getAssociations() {
        return associations;
    }

    public String getPassword() {
        return password;
    }

    // </editor-fold>

}
