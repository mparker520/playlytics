package com.mparker.playlytics.composite_ids;

// Imports
import jakarta.persistence.*;
import java.io.Serializable;


@Embeddable

public class ConfirmedConnectionId implements Serializable {

    // Database Columns

    @Column(name = "registered_player_A_id")
    private long registeredPlayerAId;

    @Column(name = "registered_player_B_id")
    private long registeredPlayerBId;

}
