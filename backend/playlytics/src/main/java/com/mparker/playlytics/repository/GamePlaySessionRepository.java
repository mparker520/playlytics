package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.GamePlaySession;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GamePlaySessionRepository extends JpaRepository<GamePlaySession, Long> {

}
