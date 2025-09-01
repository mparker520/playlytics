package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredPlayerRepository extends JpaRepository<RegisteredPlayer, Long> {
}
