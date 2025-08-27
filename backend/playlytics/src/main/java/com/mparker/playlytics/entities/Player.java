package com.mparker.playlytics.entities;

// Imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="players")

public class Player {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    private String lastName;


    @Lob
    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;


    @CreationTimestamp
    @Column(name = "creation_timestamp", updatable = false)
    private Instant creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Instant updateTimestamp;






    // GamePlaySessions are Mapped via the SessionParticipant Associative Entity
    // See SessionParticipant.java


}
