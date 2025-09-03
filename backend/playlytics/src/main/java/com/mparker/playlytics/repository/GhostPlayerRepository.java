package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GhostPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface GhostPlayerRepository extends JpaRepository<GhostPlayer, Long> {

    GhostPlayer findGhostPlayerByIdentifierEmail(String identifierEmailNormalized);

}
