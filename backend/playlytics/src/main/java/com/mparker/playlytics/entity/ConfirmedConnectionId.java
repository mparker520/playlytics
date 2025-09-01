package com.mparker.playlytics.entity;

// Imports
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable

public class ConfirmedConnectionId implements Serializable {

    // <editor-fold desc="Variable Fields">

    private Long registeredPlayerAId;

    private Long registeredPlayerBId;

    // </editor-fold>


    // <editor-fold desc="Equals and HashCode">

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

    // </editor-fold>

}
