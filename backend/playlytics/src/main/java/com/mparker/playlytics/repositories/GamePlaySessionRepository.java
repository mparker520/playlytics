package com.mparker.playlytics.repositories;

// Imports
import com.mparker.playlytics.entities.GamePlaySession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlaySessionRepository extends JpaRepository<GamePlaySession, Long> {
}
