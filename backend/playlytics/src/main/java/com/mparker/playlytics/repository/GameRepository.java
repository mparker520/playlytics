package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    // Get all Games starting with Filter, order by name
    Set<Game> findByGameTitleStartingWithOrderByGameTitle(String title);

    // Get all Games by Containing Filter, ignoring case
    Set<Game> findByGameTitleContainingIgnoreCase(String title);

    Game getGameByGameTitleIgnoreCase(String boardGame);
}
