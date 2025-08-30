package com.mparker.playlytics.repositories;

// Imports
import com.mparker.playlytics.entities.SessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Long> {

    // Is Player a participant in GamePlaySession
    public boolean existsByGamePlaySession_IdAndPlayer_Id(Long gamePlaySessionId, Long playerId);

}
