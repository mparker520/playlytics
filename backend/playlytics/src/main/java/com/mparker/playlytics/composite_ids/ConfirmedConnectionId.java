package com.mparker.playlytics.composite_ids;

// Imports
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable

public class ConfirmedConnectionId implements Serializable {

    // Database Columns

    @Column(name = "registered_player_A_id", updatable = false)
    private long registeredPlayerAId;

    @Column(name = "registered_player_B_id", updatable = false)
    private long registeredPlayerBId;


    // Equals and HashCode Override Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmedConnectionId that = (ConfirmedConnectionId) o;
        return registeredPlayerAId == that.registeredPlayerAId && registeredPlayerBId == that.registeredPlayerBId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredPlayerAId, registeredPlayerBId);
    }

}
