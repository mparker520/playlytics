package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GhostPlayerRepository extends JpaRepository<GhostPlayer, Long> {

    GhostPlayer findByIdentifierEmail(String identifierEmailNormalized);

}
