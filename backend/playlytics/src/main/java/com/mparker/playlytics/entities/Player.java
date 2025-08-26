package com.mparker.playlytics.entities;

// Imports
import com.mparker.playlytics.enums.RegistrationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;


@Entity
// Player is joined to User through Inheritance via Player id being inherited as User id
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="players")

public class Player {

    // Database Columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "display_name", nullable = false)
    @NotNull
    private String displayName;

    @Lob
    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;

    @Column(name = "registration_status")
    private RegistrationStatus registrationStatus;

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;


    // Maps to RegisteredPlayer to Indicate who Created this Player
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private RegisteredPlayer creatorId;

    // GamePlaySessions are Mapped via the SessionParticipant Associative Entity
    // See SessionParticipant.java


}
