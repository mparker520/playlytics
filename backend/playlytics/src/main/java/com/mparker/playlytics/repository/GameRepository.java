package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
