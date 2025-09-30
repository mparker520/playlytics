package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {

    Set<Game> findByGameTitleStartingWithOrderByGameTitle(String title);

    Set<Game> findByGameTitleContainingIgnoreCase(String title);
}
