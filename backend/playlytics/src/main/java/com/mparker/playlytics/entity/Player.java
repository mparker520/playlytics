package com.mparker.playlytics.entity;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="players", indexes = {
        @Index(name = "multiIndex_players_first_last", columnList = "first_name, last_name"),
        @Index(name = "multiIndex_players_last_first", columnList = "last_name, first_name"),

})

public class Player {

    // <editor-fold desc = "Database Columns">

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Integer version;

    @Column(name = "first_name", nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    private String lastName;


    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;



    // </editor-fold>

    // <editor-fold desc = "Relationship Mappings">
    // GamePlaySessions are Mapped via the SessionParticipant Associative Entity
    // See SessionParticipant.java
    // </editor-fold >

    // <editor-fold desc = "Equals and HashCode">

    // Establish uid for comparison and hashing
    @Column (name = "uid", nullable = false, updatable = false, unique = true)
    private UUID uid = UUID.randomUUID();

    // Define Equals
    @Override
    public boolean equals(Object o) {

        // If same in memory, equal is true
        if (this == o) return true;

        // If Object is null, equal is false
        if (o == null) return false;

        // If the instances of this and object are not equal, false
        if (!(o instanceof Player that)) return false;

        // If this uid and object uid are equal, return true
        return uid.equals(that.uid);

    }

    // Hash Based on UID
    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    // </editor-fold>

    // <editor-fold desc="Constructors">

    public Player() {
    }

    public Player(String firstName, String lastName, byte[] avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    // </editor-fold>

    // <editor-fold desc="Getters and Setters">

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }



    // </editor-fold>


}
