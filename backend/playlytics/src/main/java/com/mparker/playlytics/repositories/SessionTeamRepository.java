package com.mparker.playlytics.repositories;

// Imports
import com.mparker.playlytics.entities.SessionTeam;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionTeamRepository extends JpaRepository<SessionTeam, Long> {
}
