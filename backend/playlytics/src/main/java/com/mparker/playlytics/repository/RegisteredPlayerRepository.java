package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredPlayerRepository extends JpaRepository<RegisteredPlayer, Long> {

    RegisteredPlayer findByLoginEmail(String email);

    void deleteAssociationByIdAndGhostPlayerId(Long registeredPlayerId, Long ghostPlayerId);

    boolean existsByIdAndAssociations(Long registeredPlayerId, GhostPlayer ghostPlayer);

}
