package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GamePlaySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface GamePlaySessionRepository extends JpaRepository<GamePlaySession, Long> {

    Set<GamePlaySession> findAllByCreator_Id(Long registeredPlayerId);

}
